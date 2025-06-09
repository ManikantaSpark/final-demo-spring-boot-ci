package com.example.Final.Demo.Spring.Project.config;

import com.example.Final.Demo.Spring.Project.model.Product;
import com.example.Final.Demo.Spring.Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            List<Product> sampleProducts = Arrays.asList(
                new Product(null, "Laptop", "High-performance laptop for work and gaming", 999.99, true, "laptop.jpg", "Electronics"),
                new Product(null, "Smartphone", "Latest model smartphone with advanced features", 699.99, true, "smartphone.jpg", "Electronics"),
                new Product(null, "Headphones", "Noise-cancelling wireless headphones", 199.99, true, "headphones.jpg", "Electronics"),
                new Product(null, "Coffee Maker", "Automatic coffee maker with timer", 89.99, true, "coffee-maker.jpg", "Home & Kitchen"),
                new Product(null, "Running Shoes", "Comfortable running shoes for daily exercise", 129.99, true, "running-shoes.jpg", "Sports"),
                new Product(null, "Book - Java Programming", "Complete guide to Java programming", 39.99, true, "java-book.jpg", "Books"),
                new Product(null, "Wireless Mouse", "Ergonomic wireless mouse", 29.99, false, "wireless-mouse.jpg", "Electronics"),
                new Product(null, "Water Bottle", "Stainless steel water bottle", 24.99, true, "water-bottle.jpg", "Sports")
            );
            
            productRepository.saveAll(sampleProducts);
            System.out.println("Sample products initialized!");
        }
    }
}
