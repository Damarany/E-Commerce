package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.OrderDto;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.model.Order;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ResponseAPI> createOrder(@RequestParam Long userId){
        try {
            OrderDto order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ResponseAPI("Item order success",order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseAPI("Error MSG",e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ResponseAPI> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto orderDto = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ResponseAPI("Item order success",orderDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseAPI("Error MSG",e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ResponseAPI> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> ordersDto = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ResponseAPI("Item order success",ordersDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseAPI("Error MSG",e.getMessage()));
        }
    }

}











