package com.example.Final.Demo.Spring.Project.repository;

import com.example.Final.Demo.Spring.Project.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    
    Optional<CartItem> findByProductId(String productId);
    
    void deleteByProductId(String productId);
}
