package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.model.CartItem;

public interface ICartItemService {
    public void addItemToCart(Long cartId,Long productId,int qty);
    public void removeItemFromCart(Long cartId,Long productId);
    public void updateItemQty(Long cartId,Long productId,int qty);

    CartItem getCartItem(Long cartId, Long productId);
}
