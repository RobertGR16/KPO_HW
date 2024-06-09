package ru.hse.ticketservice.service

import org.springframework.stereotype.Service
import ru.hse.ticketservice.exception.TicketServiceException
import ru.hse.ticketservice.model.Order
import ru.hse.ticketservice.repository.OrderRepository
import ru.hse.ticketservice.repository.StationRepository

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val stationRepository: StationRepository,
) {

    fun createOrder(userId: Long, fromStationId: Long, toStationId: Long): Order {
        val fromStation = stationRepository.findById(fromStationId).orElseThrow { TicketServiceException("Invalid from station") }
        val toStation = stationRepository.findById(toStationId).orElseThrow { TicketServiceException("Invalid to station") }
        if (fromStation.id == toStation.id) throw TicketServiceException("Stations are the same")
        val newOrder = Order(
            userId = userId,
            fromStationId = fromStation.id,
            toStationId = toStation.id,
            status = 0,
        )
        val order = orderRepository.save(newOrder)
        return order
    }

    fun getOrderById(userId: Long, orderId: Long): Order {
        val order = orderRepository.findById(orderId).orElseThrow { TicketServiceException("No such order") }
        if (order.userId != userId) throw TicketServiceException("Order does not belong to user")
        return order
    }

    fun getOrders(userId: Long): List<Order> {
        return orderRepository.findByUserId(userId)
    }

    fun getOrdersByStatus(status: Int): List<Order> {
        return orderRepository.findByStatus(status)
    }

    fun updateOrder(order: Order): Order {
        return orderRepository.save(order)
    }

    fun getOrderStatus(order: Order) : String {
        return when (order.status) {
            0.toShort() -> "Сhecking"
            1.toShort() -> "Success"
            2.toShort() -> "Rejected"
            else -> "Unknown"
        }
    }

}
