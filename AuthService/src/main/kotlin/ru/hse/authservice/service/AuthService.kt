package ru.hse.authservice.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.hse.authservice.dto.Response
import ru.hse.authservice.dto.ResponseData
import ru.hse.authservice.exception.AuthServiceException
import ru.hse.authservice.model.Session
import ru.hse.authservice.model.User
import ru.hse.authservice.repository.SessionRepository
import ru.hse.authservice.repository.UserRepository
import ru.hse.authservice.util.JwtUtil
import java.util.*
import java.util.regex.Pattern

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
) {
    @Value("\${regexp.email}")
    private lateinit var emailPattern: Pattern

    @Value("\${regexp.password}")
    private lateinit var passwordPattern: Pattern

    fun register(nickname: String, email: String, password: String): Pair<String, Date> {
        if (nickname.isBlank()) throw AuthServiceException("Nickname cannot be empty")
        if (email.isBlank()) throw AuthServiceException("Email cannot be empty")
        if (password.isBlank()) throw AuthServiceException("Password cannot be empty")

        if (!emailPattern.matcher(email).matches()) throw AuthServiceException("Incorrect email")
        if (!passwordPattern.matcher(password).matches()) throw AuthServiceException("Incorrect password")
        if (userRepository.findByNickname(nickname) != null) throw AuthServiceException("User with this nickname already exists")
        if (userRepository.findByEmail(email) != null) throw AuthServiceException("User with this email already exists")

        val user = User(
            nickname = nickname,
            email = email,
            password = passwordEncoder.encode(password)
        )
        val insertedUser = userRepository.save(user)
        return createSession(insertedUser.id)
    }

    fun auth(email: String, password: String): Pair<String, Date> {
        val user = userRepository.findByEmail(email) ?: throw AuthServiceException("No user with this email")
        if (!passwordEncoder.matches(password, user.password)) throw AuthServiceException("Incorrect password")
        return createSession(user.id)
    }

    fun info(token: String): User {
        if (!jwtUtil.validateToken(token)) throw AuthServiceException("Invalid token")
        sessionRepository.findByToken(token)
        val session = sessionRepository.findByToken(token) ?: throw AuthServiceException("No session found")

        if (session.expires.before(Date())) {
            sessionRepository.delete(session)
            throw AuthServiceException("Session expired")
        }

        val userId = jwtUtil.getUserIdFromToken(token)
        val user = userRepository.findById(userId)
            .orElseThrow { AuthServiceException("User not found") }
        return user
    }

    fun createSession(userId: Long): Pair<String, Date> {
        val (token, expDate) = jwtUtil.generateToken(userId)
        var session = sessionRepository.findByUserId(userId)
        session?.let { sessionRepository.delete(it) }
        session = Session(
            userId = userId,
            token = token,
            expires = expDate
        )
        sessionRepository.save(session)
        return token to expDate
    }

    fun <T : ResponseData> saveTokenToCookie(
        tokenData: Pair<String, Date>,
        responseEntity: ResponseEntity<Response<T>>,
    ): ResponseEntity<Response<T>> {
        val responseCookie = ResponseCookie.from("token", tokenData.first)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge((tokenData.second.time - System.currentTimeMillis()) / 1000)
            .build()

        return ResponseEntity.status(responseEntity.statusCode)
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .body(Response(responseEntity.body!!.message, responseEntity.body!!.data))
    }


}
