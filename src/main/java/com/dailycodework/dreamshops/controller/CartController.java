package com.dailycodework.dreamshops.controller;


import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.CartService;
import com.dailycodework.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart=cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success",cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/clear/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear cart Success",null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/amount/{cartId}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal amount=cartService.getTotalAmount(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", amount));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getCartByUserId(@PathVariable Long userId){
        try {
            Cart cart=cartService.getCartByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("Success",cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}