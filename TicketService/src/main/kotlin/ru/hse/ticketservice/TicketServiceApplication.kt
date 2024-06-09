package ru.hse.ticketservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.hse.ticketservice.dto.Response
import ru.hse.ticketservice.dto.ResponseData
import ru.hse.ticketservice.exception.TicketServiceException


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(TicketServiceException::class)
    fun handleTicketServiceException(exception: TicketServiceException): ResponseEntity<Response<ResponseData>> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response(exception.message ?: "Bad request"))
    }
}

@SpringBootApplication
@EnableScheduling
class OrderHandlerEmulatorApplication

@SpringBootApplication
class TicketServiceApplication

fun main(args: Array<String>) {
    runApplication<TicketServiceApplication>(*args)
}
