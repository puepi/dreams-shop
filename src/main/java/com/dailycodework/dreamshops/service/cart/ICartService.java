package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalAmount(Long id);

    Long initializeNewCart();

    Cart getCartByUserId(Long id);
}
