package ru.hse.ticketservice.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.random.Random


@Component
class ProcessOrderService @Autowired constructor(private val orderService: OrderService) {
    @Scheduled(fixedRate = 10000) // 10 seconds
    fun processOrders() {
        val readyOrders = orderService.getOrdersByStatus(0)
        for (order in readyOrders) {
            val isSuccess = Random.nextBoolean()
            if (isSuccess) {
                order.status = 1
            } else {
                order.status = 2
            }
            orderService.updateOrder(order)
        }
    }
}
