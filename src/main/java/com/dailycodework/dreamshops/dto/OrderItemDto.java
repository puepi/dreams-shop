package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private String productName;
    private int qty;
    private BigDecimal price;
}
