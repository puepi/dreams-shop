package com.dailycodework.dreamshops.model;

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

    private List<Image> images;

}
