package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class CartItemDto {
    private Long id;
    private ProductDto product;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Cart cart;
}
