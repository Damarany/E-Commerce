package com.ARD.eCommerce.services.category;

import com.ARD.eCommerce.dtos.CategoryDto;
import com.ARD.eCommerce.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(CategoryDto categoryDto);
    Category updateCategory(CategoryDto categoryDto,Long id);
    void deleteCategoryById(Long id);
}
