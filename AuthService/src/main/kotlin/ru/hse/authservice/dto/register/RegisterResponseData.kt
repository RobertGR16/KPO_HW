package ru.hse.authservice.dto.register

import ru.hse.authservice.dto.ResponseData

data class RegisterResponseData(
    val token: String,
) : ResponseData()