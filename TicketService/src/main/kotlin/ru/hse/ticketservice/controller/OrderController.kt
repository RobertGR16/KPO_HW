package ru.hse.ticketservice.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.ticketservice.dto.Response
import ru.hse.ticketservice.dto.createOrder.CreateOrderRequest
import ru.hse.ticketservice.dto.createOrder.CreateOrderResponseData
import ru.hse.ticketservice.dto.infoOrder.InfoOrderResponseData
import ru.hse.ticketservice.dto.infoOrders.InfoOrdersResponseData
import ru.hse.ticketservice.service.OrderService
import ru.hse.ticketservice.util.AuthenticationAspect
import ru.hse.ticketservice.util.Login

@RestController
@RequestMapping("/order")
class OrderController(private val orderService: OrderService) {

    @PostMapping("/create")
    @Login
    fun createOrder(@Valid @RequestBody createOrderRequest: CreateOrderRequest): ResponseEntity<Response<CreateOrderResponseData>> {
        val userId =
            AuthenticationAspect.getUserId() ?: return ResponseEntity.status(401).body(Response("Unauthorized", null))
        val order = orderService.createOrder(userId, createOrderRequest.fromStationId, createOrderRequest.toStationId)
        val orderStatus = orderService.getOrderStatus(order)
        val message = "Order created"
        val responseEntity = ResponseEntity.ok(
            Response(
                message,
                CreateOrderResponseData(order.id, order.userId, order.fromStationId, order.toStationId, orderStatus)
            )
        )
        return responseEntity
    }

    @GetMapping("/{orderId}")
    @Login
    fun getOrderById(@PathVariable orderId: Long): ResponseEntity<Response<InfoOrderResponseData>> {
        val userId =
            AuthenticationAspect.getUserId() ?: return ResponseEntity.status(401).body(Response("Unauthorized", null))
        val order = orderService.getOrderById(userId, orderId)
        val orderStatus = orderService.getOrderStatus(order)
        val message = "Order info"
        val responseEntity = ResponseEntity.ok(
            Response(
                message,
                InfoOrderResponseData(order.id, order.userId, order.fromStationId, order.toStationId, orderStatus)
            )
        )
        return responseEntity
    }

    @GetMapping("/all")
    @Login
    fun getOrdersByStatus(): ResponseEntity<Response<InfoOrdersResponseData>> {
        val userId =
            AuthenticationAspect.getUserId() ?: return ResponseEntity.status(401).body(Response("Unauthorized", null))
        val orders = orderService.getOrders(userId)
        val message = "Orders info"
        val responseData = InfoOrdersResponseData(orders.mapIndexed { index, order ->
            InfoOrderResponseData(
                order.id,
                order.userId,
                order.fromStationId,
                order.toStationId,
                orderService.getOrderStatus(order)
            )
        })
        val responseEntity = ResponseEntity.ok(Response(message, responseData))
        return responseEntity
    }
}