package ru.hse.ticketservice.repository

import ru.hse.ticketservice.model.Station
import org.springframework.data.jpa.repository.JpaRepository

interface StationRepository : JpaRepository<Station, Long> {

}
