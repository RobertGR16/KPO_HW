package ru.hse.authservice.dto

class Response<T : ResponseData> (
    var message: String? = null,
    var data: T? = null
)