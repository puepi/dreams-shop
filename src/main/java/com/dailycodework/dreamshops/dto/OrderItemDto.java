package com.dailycodework.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private String productName;
    private int qty;
    private BigDecimal price;
}
