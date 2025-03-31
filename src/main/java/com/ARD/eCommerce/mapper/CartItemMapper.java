package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.CartDto;
import com.ARD.eCommerce.dtos.CartItemDto;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CartItemMapper {
    public CartItem cartItemDtoToCartItem(CartItemDto cartItemDto);
    public CartItemDto CartItemToDto(CartItem cartItem);
}
