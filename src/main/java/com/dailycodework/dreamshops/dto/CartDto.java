package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private BigDecimal amount=BigDecimal.ZERO;
    private Set<CartItemDto> cartItems=new HashSet<>();

}
