package com.example.Final.Demo.Spring.Project.service;

import com.example.Final.Demo.Spring.Project.model.Product;
import com.example.Final.Demo.Spring.Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<Product> getFilteredProducts(String category, Boolean available) {
        return productRepository.findByFilters(category, available);
    }
    
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
