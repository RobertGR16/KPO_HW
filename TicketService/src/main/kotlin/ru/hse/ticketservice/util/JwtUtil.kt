package ru.hse.ticketservice.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    fun validateToken(token: String): Boolean {
        return try {
            System.currentTimeMillis() < Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.expiration.time
        } catch (e: Exception) {
            false
        }
    }

    fun getUserIdFromToken(token: String): Long? {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject.toLong()
        } catch (e: Exception) {
            null
        }
    }
}
