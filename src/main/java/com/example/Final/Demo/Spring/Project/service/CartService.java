package com.example.Final.Demo.Spring.Project.service;

import com.example.Final.Demo.Spring.Project.model.Cart;
import com.example.Final.Demo.Spring.Project.model.CartItem;
import com.example.Final.Demo.Spring.Project.model.Product;
import com.example.Final.Demo.Spring.Project.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductService productService;
    
    public Cart getCurrentCart() {
        List<CartItem> items = cartItemRepository.findAll();
        Double totalPrice = calculateTotalPrice(items);
        Integer itemCount = items.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
        
        return new Cart(items, totalPrice, itemCount);
    }
    
    public void addItemToCart(CartItem cartItem) {
        Optional<CartItem> existingItem = cartItemRepository.findByProductId(cartItem.getProductId());
        
        if (existingItem.isPresent()) {
            CartItem existing = existingItem.get();
            existing.setQuantity(existing.getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(existing);
        } else {
            cartItemRepository.save(cartItem);
        }
    }
    
    public void updateItemQuantity(String itemId, CartItem updatedItem) {
        Optional<CartItem> existingItem = cartItemRepository.findById(itemId);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(updatedItem.getQuantity());
            cartItemRepository.save(item);
        }
    }
    
    @Transactional
    public void removeItemFromCart(String itemId) {
        cartItemRepository.deleteById(itemId);
    }
    
    @Transactional
    public void clearCart() {
        cartItemRepository.deleteAll();
    }
    
    private Double calculateTotalPrice(List<CartItem> items) {
        return items.stream()
            .mapToDouble(item -> {
                Optional<Product> product = productService.getProductById(item.getProductId());
                return product.map(p -> p.getPrice() * item.getQuantity()).orElse(0.0);
            })
            .sum();
    }
}
