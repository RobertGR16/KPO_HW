package ru.hse.ticketservice.repository

import ru.hse.ticketservice.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByStatus(status: Int): List<Order>
    fun findByUserId(userId: Long): List<Order>
}
