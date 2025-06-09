package com.example.Final.Demo.Spring.Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    
    @Column(length = 2000)
    private String cartItems; // JSON representation of cart
    
    @Column(nullable = false)
    private Double totalPrice;
    
    @Column(nullable = false)
    private Integer itemCount;
    
    @Column(nullable = false)
    private String shippingAddress;
    
    @Column(nullable = false)
    private String paymentMethod;
    
    @Column(nullable = false)
    private String orderStatus = "PENDING";
}
