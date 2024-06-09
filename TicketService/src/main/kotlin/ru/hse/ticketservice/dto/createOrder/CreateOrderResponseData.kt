package ru.hse.ticketservice.dto.createOrder

import ru.hse.ticketservice.dto.ResponseData

data class CreateOrderResponseData(
    val orderId: Long,
    val userId : Long,
    val fromStationId : Long,
    val toStationId : Long,
    val status : String
) : ResponseData()