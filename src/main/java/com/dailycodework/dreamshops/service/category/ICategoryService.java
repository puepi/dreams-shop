package com.dailycodework.dreamshops.service.category;

import com.dailycodework.dreamshops.model.Category;

public interface ICategoryService {
    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String categoryName);
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategoryById(Long id);
}
