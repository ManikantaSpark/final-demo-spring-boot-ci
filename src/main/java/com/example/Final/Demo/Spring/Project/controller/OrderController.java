package com.example.Final.Demo.Spring.Project.controller;

import com.example.Final.Demo.Spring.Project.dto.OrderRequest;
import com.example.Final.Demo.Spring.Project.model.Order;
import com.example.Final.Demo.Spring.Project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(
            orderRequest.getShippingAddress(), 
            orderRequest.getPaymentMethod()
        );
        return ResponseEntity.ok("Order placed successfully with ID: " + order.getOrderId());
    }
    
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);
        
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
