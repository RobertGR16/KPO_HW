package ru.hse.ticketservice.service

import org.springframework.stereotype.Service
import ru.hse.ticketservice.repository.StationRepository
import ru.hse.ticketservice.model.Station

@Service
class StationService(
    private val stationRepository: StationRepository,
) {
    fun getAllStations(): List<Station> {
        val stations = stationRepository.findAll()
        return stations
    }
}
