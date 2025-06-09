package com.example.Final.Demo.Spring.Project.repository;

import com.example.Final.Demo.Spring.Project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
