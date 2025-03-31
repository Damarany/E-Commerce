package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.CategoryDto;
import com.ARD.eCommerce.exceptions.AlreadyExistsExeption;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.model.Category;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ResponseAPI> getAllGategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ResponseAPI("Found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseAPI("ERROR!",HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseAPI>addCategory(@RequestBody CategoryDto  categoryDto){
        try {
            categoryService.addCategory(categoryDto);
            return ResponseEntity.ok(new ResponseAPI("done",categoryDto));
        } catch (AlreadyExistsExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseAPI(e.getMessage(), null));
        }
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseAPI>getCategoryById(@PathVariable Long id){
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ResponseAPI("Found",category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @GetMapping("/category/name/{name}")
    public ResponseEntity<ResponseAPI> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ResponseAPI("Found",category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<ResponseAPI> deleteCategoryById(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ResponseAPI("Deleted category with id: " + id,null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }
    @PutMapping("/category/update/{id}")
    public ResponseEntity<ResponseAPI> updateCategoryById(@RequestBody CategoryDto categoryDto,@PathVariable Long id){
        try {
            Category category = categoryService.updateCategory(categoryDto,id);
            return ResponseEntity.ok(new ResponseAPI("updated successfully",category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }
}
