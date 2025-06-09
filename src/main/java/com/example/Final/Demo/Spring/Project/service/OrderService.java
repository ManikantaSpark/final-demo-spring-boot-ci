package com.example.Final.Demo.Spring.Project.service;

import com.example.Final.Demo.Spring.Project.model.Cart;
import com.example.Final.Demo.Spring.Project.model.Order;
import com.example.Final.Demo.Spring.Project.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public Order createOrder(String shippingAddress, String paymentMethod) {
        Cart currentCart = cartService.getCurrentCart();
        
        Order order = new Order();
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setTotalPrice(currentCart.getTotalPrice());
        order.setItemCount(currentCart.getItemCount());
        order.setOrderStatus("CONFIRMED");
        
        try {
            order.setCartItems(objectMapper.writeValueAsString(currentCart.getItems()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing cart items", e);
        }
        
        Order savedOrder = orderRepository.save(order);
        
        // Clear the cart after order is placed
        cartService.clearCart();
        
        return savedOrder;
    }
    
    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }
}
