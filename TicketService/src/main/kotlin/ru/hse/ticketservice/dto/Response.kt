package ru.hse.ticketservice.dto

class Response<T : ResponseData> (
    var message: String? = null,
    var data: T? = null
)