package com.example.Final.Demo.Spring.Project.repository;

import com.example.Final.Demo.Spring.Project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    List<Product> findByCategory(String category);
    
    List<Product> findByAvailable(Boolean available);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:available IS NULL OR p.available = :available)")
    List<Product> findByFilters(@Param("category") String category, 
                               @Param("available") Boolean available);
}
