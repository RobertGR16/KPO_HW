package ru.hse.ticketservice.dto.infoOrder

import ru.hse.ticketservice.dto.ResponseData

data class InfoOrderResponseData(
    val orderId: Long,
    val userId : Long,
    val fromStationId : Long,
    val toStationId : Long,
    val status : String
) : ResponseData()