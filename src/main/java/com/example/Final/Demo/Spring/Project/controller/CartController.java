package com.example.Final.Demo.Spring.Project.controller;

import com.example.Final.Demo.Spring.Project.dto.CartItemRequest;
import com.example.Final.Demo.Spring.Project.model.Cart;
import com.example.Final.Demo.Spring.Project.model.CartItem;
import com.example.Final.Demo.Spring.Project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public ResponseEntity<Cart> getCurrentCart() {
        Cart cart = cartService.getCurrentCart();
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping
    public ResponseEntity<String> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemRequest.getProductId());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        
        cartService.addItemToCart(cartItem);
        return ResponseEntity.ok("Item added to cart");
    }
    
    @PutMapping("/{itemId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable String itemId, 
            @RequestBody CartItemRequest cartItemRequest) {
        
        CartItem updatedItem = new CartItem();
        updatedItem.setQuantity(cartItemRequest.getQuantity());
        
        cartService.updateItemQuantity(itemId, updatedItem);
        return ResponseEntity.ok("Item quantity updated");
    }
    
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable String itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok("Item removed from cart");
    }
}
