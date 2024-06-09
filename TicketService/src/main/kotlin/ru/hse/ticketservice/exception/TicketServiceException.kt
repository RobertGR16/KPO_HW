package ru.hse.ticketservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class TicketServiceException(message: String) : RuntimeException(message)
