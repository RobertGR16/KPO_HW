package ru.hse.ticketservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.ticketservice.dto.Response
import ru.hse.ticketservice.dto.infoStations.InfoStationsResponseData
import ru.hse.ticketservice.service.StationService


@RestController
@RequestMapping("/station")
class StationController(private val stationService: StationService) {

    @GetMapping("/all")
    fun getAllStations(): ResponseEntity<Response<InfoStationsResponseData>> {
        val stations = stationService.getAllStations()
        val message = "Stations info"
        val responseData = InfoStationsResponseData(stations)
        val responseEntity = ResponseEntity.ok(Response(message, responseData))
        return responseEntity
    }
}