package ru.hse.authservice.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.authservice.dto.Response
import ru.hse.authservice.dto.auth.AuthRequest
import ru.hse.authservice.dto.auth.AuthResponseData
import ru.hse.authservice.dto.info.InfoResponseData
import ru.hse.authservice.dto.register.RegisterRequest
import ru.hse.authservice.dto.register.RegisterResponseData
import ru.hse.authservice.exception.AuthServiceException
import ru.hse.authservice.service.AuthService

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody registerRequest : RegisterRequest): ResponseEntity<Response<RegisterResponseData>> {
        val token =  authService.register(registerRequest.nickname, registerRequest.email, registerRequest.password)
        val message = "User ${registerRequest.nickname} registered"
        val responseData = RegisterResponseData(token.first)
        var responseEntity = ResponseEntity.ok(Response(message, responseData))
        responseEntity = authService.saveTokenToCookie(token, responseEntity)
        return responseEntity
    }

    @PostMapping("/login")
    fun auth(@RequestBody authRequest: AuthRequest): ResponseEntity<Response<AuthResponseData>> {
        val token = authService.auth(authRequest.email, authRequest.password)
        val message = "User ${authRequest.email} logged in"
        val responseData = AuthResponseData(token.first)
        var responseEntity = ResponseEntity.ok(Response(message, responseData))
        responseEntity = authService.saveTokenToCookie(token, responseEntity)
        return responseEntity
    }

    @GetMapping("/info")
    fun info(
        @RequestHeader("Authorization", required = false) authHeader: String?,
        @CookieValue("token", required = false) tokenCookie: String?,
        @RequestBody(required = false) body: Map<String, String>?
    ): ResponseEntity<Response<InfoResponseData>> {

        val token = authHeader?.removePrefix("Bearer ")
            ?: body?.get("token")
            ?: tokenCookie
            ?: throw AuthServiceException("No token")
        val user = authService.info(token)
        val message = "User info"
        val responseData = InfoResponseData(user.nickname, user.email)
        return ResponseEntity.ok(Response(message, responseData))
    }
}

