package com.ARD.eCommerce.services.product;

import com.ARD.eCommerce.dtos.AddProductDto;
import com.ARD.eCommerce.dtos.UpdateProductDto;
import com.ARD.eCommerce.exceptions.AlreadyExistsExeption;
import com.ARD.eCommerce.exceptions.ProductNotFoundException;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.ProductMapper;
import com.ARD.eCommerce.model.Category;
import com.ARD.eCommerce.model.Product;
import com.ARD.eCommerce.repository.CategoryRepo;
import com.ARD.eCommerce.repository.ProductRepo;
import com.ARD.eCommerce.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class productServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    @Override
    public Product addProduct(AddProductDto addProductDto) {
        Product product = productMapper.addProductDtotoProduct(addProductDto);
//        Category category = categoryRepo.findById(product.getCategory().getId()).
//                orElseThrow(()->new RuntimeException("Category not found!"));
        if(productRepo.existsByNameAndBrand(product.getName(), product.getBrand())){
            throw new AlreadyExistsExeption("this product is already exist ,, you can update it instead!!");
        }
        Category category  = categoryService.getCategoryByName(product.getCategory().getName());
        if (categoryRepo.existsByName(category.getName())){
            product.setCategory(category);
        }else{
            throw new ResourceNotFoundException("this Category is mistake or not found");
        }
        return productRepo.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Product not found for ID: " + id));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id).
                ifPresentOrElse(productRepo::delete, ()->{throw new ResourceNotFoundException("ID not Found!!");});
    }

    @Override
    public Product updateProductById(UpdateProductDto updateProductDto, Long id) {

            Product product = productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found!"));
            Category category = categoryRepo.findById(updateProductDto.getCategoryId())
                    .orElseThrow(()->new ResourceNotFoundException("Category not found!"));
            product.setCategory(category);
        Product updatedProduct = productMapper.updateProductDtoToProduct(updateProductDto, product);
        return productRepo.save(updatedProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


    @Override
    public List getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepo.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByBrandAndName(brand,name);
    }
}
