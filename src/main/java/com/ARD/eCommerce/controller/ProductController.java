package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.AddProductDto;
import com.ARD.eCommerce.dtos.ProductDto;
import com.ARD.eCommerce.dtos.UpdateProductDto;
import com.ARD.eCommerce.exceptions.AlreadyExistsExeption;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.ProductMapper;
import com.ARD.eCommerce.model.Product;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;


    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('VIEW')")
    @PreAuthorize("hasAuthority('VIEW-PRODUCT')")
    public ResponseEntity<ResponseAPI>getAllProduct(){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = productMapper.ListToProductsDto(products);
        return ResponseEntity.ok(new ResponseAPI("Done",productDtos));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseAPI>getProductById(@PathVariable Long id){
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productMapper.ToProductDto(product);
            return ResponseEntity.ok(new ResponseAPI("Done",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }



    @PostMapping("/add")
    public ResponseEntity<ResponseAPI> addProduct(@RequestBody AddProductDto addProductDto){
        try {
            Product product = productService.addProduct(addProductDto);
            AddProductDto dto = productMapper.productToProductDto(product);
            return ResponseEntity.ok(new ResponseAPI("done",dto));
        } catch (AlreadyExistsExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseAPI(e.getMessage(), null));        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ResponseAPI> updateProduct(@RequestBody UpdateProductDto productDto,@PathVariable Long productId){
        try {
            Product product = productService.updateProductById(productDto, productId);
            UpdateProductDto updatedProduct = productMapper.productToUpdateProductDto(product);
            return ResponseEntity.ok(new ResponseAPI("updated done",updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ResponseAPI>deleteProductById(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ResponseAPI("Product has been deleted with ID: " + productId,null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @GetMapping("/brand-name")
    public ResponseEntity<ResponseAPI>getProductsByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                        (new ResponseAPI("No products with this name or brand",null));
            }
            List<ProductDto> productDtos = productMapper.ListToProductsDto(products);
            return ResponseEntity.ok(new ResponseAPI("DONE",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(e.getMessage(), null));
        }

    }

    @GetMapping("/category-brand")
    public ResponseEntity<ResponseAPI>getProductsByCategoryAndBrand(@RequestParam String category,@RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                        (new ResponseAPI("No products with this category or brand",null));
            }
            List<ProductDto> productDtos = productMapper.ListToProductsDto(products);
            return ResponseEntity.ok(new ResponseAPI("DONE",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(e.getMessage(), null));
        }

    }

    @GetMapping("/{name}")
    public ResponseEntity<ResponseAPI>getProductsByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                        (new ResponseAPI("No products with name: " + name + "!!",null));
            }
            List<ProductDto> productDtos = productMapper.ListToProductsDto(products);
            return ResponseEntity.ok(new ResponseAPI("DONE",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @GetMapping("/{brand}/product")
    public ResponseEntity<ResponseAPI>getProductsByBrand(@PathVariable String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                        (new ResponseAPI("No products with brand: " + brand + "!!",null));
            }
            List<ProductDto> productDtos = productMapper.ListToProductsDto(products);
            return ResponseEntity.ok(new ResponseAPI("DONE",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @GetMapping("all/{category}")
    public ResponseEntity<ResponseAPI>getProductsByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body
                        (new ResponseAPI("No products with catecory: " + category + "!!",null));
            }
            List<ProductDto> productDtos = productMapper.ListToProductsDto(products);
            return ResponseEntity.ok(new ResponseAPI("DONE",productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @GetMapping("count/brand-name")
    public ResponseEntity<ResponseAPI>countByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            Long productsCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ResponseAPI("DONE",productsCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseAPI(e.getMessage(), null));
        }
    }

}
