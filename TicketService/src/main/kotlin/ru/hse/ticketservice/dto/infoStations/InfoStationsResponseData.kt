package ru.hse.ticketservice.dto.infoStations

import ru.hse.ticketservice.dto.ResponseData
import ru.hse.ticketservice.model.Station

data class InfoStationsResponseData(
    val stations: List<Station>,
) : ResponseData()