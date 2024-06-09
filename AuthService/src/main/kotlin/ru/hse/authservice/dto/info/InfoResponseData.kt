package ru.hse.authservice.dto.info

import ru.hse.authservice.dto.ResponseData

data class InfoResponseData(
    val nickname: String,
    val email: String,
) : ResponseData()