package com.dailycodework.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount=BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems=new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private void updateTotalAmount(){
        amount=cartItems.stream()
                .map(cartItem -> {
                        BigDecimal price=cartItem.getUnitPrice();
                        if(price==null)
                            return BigDecimal.ZERO;
                        return price.multiply(BigDecimal.valueOf(cartItem.getQty()));
                })
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem cartItem){
        cartItems.remove(cartItem);
        cartItem.setCart(null);
        updateTotalAmount();
    }
}
