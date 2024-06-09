package ru.hse.authservice.dto.auth

data class AuthRequest(
    val email: String,
    val password: String
)