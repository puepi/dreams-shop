package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartItemService;
import com.dailycodework.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart-item")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;


    @PostMapping("/add-item")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam int qty){
        try {
            User user=cartRepository
            cartId=cartService.initializeNewCart();
            cartItemService.addItemToCart(cartId,productId,qty);
            return ResponseEntity.ok(new ApiResponse("Item added succes",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long productId){
        try {
            cartItemService.removeItemFromCart(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Item deleted success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> updateItemQty(@PathVariable Long cartId,@PathVariable Long productId,@RequestParam int qty){
        try {
            cartItemService.updateItemQty(cartId,productId,qty);
            return ResponseEntity.ok(new ApiResponse("Quantity updated success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse> getItemFromCart(@PathVariable Long itemId){
        CartItem cartItem= cartItemService.getCartItem(itemId);
        return ResponseEntity.ok(new ApiResponse("Item found",cartItem));
    }
}
