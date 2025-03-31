package com.ARD.eCommerce.services.product;

import com.ARD.eCommerce.dtos.AddProductDto;
import com.ARD.eCommerce.dtos.UpdateProductDto;
import com.ARD.eCommerce.model.Category;
import com.ARD.eCommerce.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(AddProductDto addProductDto);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProductById(UpdateProductDto updateProductDto, Long id);

    List<Product> getAllProducts();

    List getProductsByCategory(String category);

    List getProductsByBrand(String brand);

    List getProductsByCategoryAndBrand(String category,String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);






}