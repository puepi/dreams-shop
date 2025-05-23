package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    //get the cart
    //get the product, check if it already exists in the cart
    //if yes, increase the qty with the requested qty
    //if no, initiate a new cartitem entry
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;
    @Override
    public void addItemToCart(Long cartId, Long productId, int qty) throws ResourceNotFoundException{
        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem cartItem=cart.getCartItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId()==null){
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setQty(qty);
        }
        cartItem.setQty(cartItem.getQty() + qty);
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart=cartService.getCart(cartId);
        Set<CartItem> items=cart.getCartItems();
        CartItem cartItem=getCartItem(cartId,productId);
        cart.removeItem(cartItem);
        cartRepository.save(cart);
     }

    @Override
    public void updateItemQty(Long cartId, Long productId, int qty) {
        Cart cart=cartService.getCart(cartId);
//        cart.getCartItems().stream().filter(item-> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .ifPresent(item-> {
//                    item.setQty(qty);
//                    item.setUnitPrice(item.getProduct().getPrice());
//                    item.setTotalPrice();
//                });
//        BigDecimal amount=cart.getAmount();
//        cart.setAmount(amount);
//        cartRepository.save(cart);
        CartItem cartItem=getCartItem(cartId,productId);
        cartItem.setQty(qty);
        cartItem.setTotalPrice();
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart=cartService.getCart(cartId);
        return cart.getCartItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }
}
