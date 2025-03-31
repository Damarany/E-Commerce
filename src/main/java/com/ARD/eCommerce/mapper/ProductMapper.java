package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.AddProductDto;
import com.ARD.eCommerce.dtos.ProductDto;
import com.ARD.eCommerce.dtos.UpdateProductDto;
import com.ARD.eCommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "category.name",source = "categoryName")
    Product addProductDtotoProduct(AddProductDto addProductDto);

//    @Mapping(target = "category.name",source = "categoryName")
    Product productDtotoProduct(ProductDto productDto);

    @Mapping(target = "categoryName",source = "category.name")
    AddProductDto productToProductDto(Product product);

//    @Mapping(target = "categoryName",source = "category.name")
    ProductDto ToProductDto(Product product);
   @Mapping(target = "category.id",source = "categoryId")
    Product updateProductDtoToProduct(UpdateProductDto updateProductDto, @MappingTarget Product product);

    @Mapping(target = "categoryId",source = "category.id")
   UpdateProductDto productToUpdateProductDto(Product product);
//    @Mapping(target = "categoryName",source = "category.name")
    List<ProductDto> ListToProductsDto(List<Product> products);
}
