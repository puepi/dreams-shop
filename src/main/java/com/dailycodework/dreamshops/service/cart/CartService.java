package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final AtomicLong cartIdGenerator=new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart=cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart doesn't exist"));
        return cart;
    }

    @Transactional
    @Override
    public void clearCart(Long id) throws ResourceNotFoundException {
        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalAmount(Long id) throws ResourceNotFoundException {
        Cart cart=getCart(id);
        return cart.getAmount();
    }

    @Override
    public Cart initializeNewCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                        .orElseGet(()->{
                            Cart cart=new Cart();
                            cart.setUser(user);
                            return cartRepository.save(cart);
                        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        if(userRepository.existsById(userId)){
            return cartRepository.findByUserId(userId);
        }else
            throw new ResourceNotFoundException("User doesn't exist");
    }

}
