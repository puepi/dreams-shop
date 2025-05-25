package com.dailycodework.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int qty;
    private BigDecimal price;

    public OrderItem(Order order,Product product, BigDecimal totalPrice, int qty) {
        this.order=order;
        this.product=product;
        this.price=totalPrice;
        this.qty=qty;
    }
}
