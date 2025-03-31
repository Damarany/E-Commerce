
package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.CartDto;
import com.ARD.eCommerce.dtos.CartItemDto;
import com.ARD.eCommerce.dtos.CategoryDto;
import com.ARD.eCommerce.dtos.ProductDto;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.CartItem;
import com.ARD.eCommerce.model.Category;
import com.ARD.eCommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartMapper {
      @Mapping(target = "items",source = "items")
    public Cart cartDtoToCart(CartDto cartDto);
    @Mapping(target = "items",source = "items")
    public CartDto CartToDto(Cart cart);

//    @Mapping(target = "cart", ignore = true) // Ignore the Cart reference in CartItem
    @Mapping(target = "product",source = "product")
    CartItemDto cartItemToCartItemDto(CartItem cartItem);

//    @Mapping(target = "categoryName",source = "category.name")
    ProductDto productToProductDto(Product product);

}
