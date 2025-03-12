package com.dailycodework.dreamshops.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;

public class Product {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int Inventory;
    private String description;

    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

}
