package ru.hse.ticketservice.dto.infoOrders

import ru.hse.ticketservice.dto.ResponseData
import ru.hse.ticketservice.dto.infoOrder.InfoOrderResponseData

data class InfoOrdersResponseData(
    val orders: List<InfoOrderResponseData>,
) : ResponseData()