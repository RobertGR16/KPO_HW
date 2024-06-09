package ru.hse.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.hse.authservice.dto.Response
import ru.hse.authservice.dto.ResponseData
import ru.hse.authservice.exception.AuthServiceException

@Configuration
class SecurityConfig {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(AuthServiceException::class)
    fun handleAuthServiceException(exception: AuthServiceException): ResponseEntity<Response<ResponseData>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response(exception.message ?: "Bad request"))
    }
    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<Response<ResponseData>> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response(exception.message ?: "Internal server error"))
    }

}

@SpringBootApplication
class AuthServiceApplication
fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
