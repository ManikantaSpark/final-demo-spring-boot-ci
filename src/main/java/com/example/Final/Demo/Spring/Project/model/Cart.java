package com.example.Final.Demo.Spring.Project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    private List<CartItem> items;
    private Double totalPrice;
    private Integer itemCount;
}
