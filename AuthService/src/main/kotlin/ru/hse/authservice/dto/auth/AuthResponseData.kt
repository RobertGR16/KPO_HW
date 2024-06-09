package ru.hse.authservice.dto.auth

import ru.hse.authservice.dto.ResponseData

data class AuthResponseData(
    val token: String,
) : ResponseData()