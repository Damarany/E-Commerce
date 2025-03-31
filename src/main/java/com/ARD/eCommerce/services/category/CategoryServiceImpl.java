package com.ARD.eCommerce.services.category;

import com.ARD.eCommerce.dtos.CategoryDto;
import com.ARD.eCommerce.exceptions.AlreadyExistsExeption;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.CategoryMapper;
import com.ARD.eCommerce.model.Category;
import com.ARD.eCommerce.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

        private final CategoryRepo categoryRepo;
        private final CategoryMapper categoryMapper;
    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found"));
        return category;
    }

    @Override
    public Category getCategoryByName(String name) {
        Optional<Category> category = Optional.ofNullable(categoryRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Category with name: " + name)));
        return category.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(CategoryDto categoryDto) {
        Category category =categoryMapper.DtoToCategory(categoryDto);
        if (categoryRepo.existsByName(category.getName())){
            throw new AlreadyExistsExeption("this Category name is already exist!");
        }else{
            return categoryRepo.save(category);
        }

    }

    @Override
    public Category updateCategory(CategoryDto categoryDto,Long id) {
        Category category = getCategoryById(id);
        Category updatedCategory = categoryMapper.updateCategoryDtoToCategory(categoryDto,category);
        return categoryRepo.save(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,()->{
            throw new ResourceNotFoundException("Category not found!");
        });
    }
}
