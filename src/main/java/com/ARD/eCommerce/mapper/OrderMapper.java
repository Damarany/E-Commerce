package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.CartItemDto;
import com.ARD.eCommerce.dtos.OrderDto;
import com.ARD.eCommerce.dtos.OrderItemDto;
import com.ARD.eCommerce.model.CartItem;
import com.ARD.eCommerce.model.Order;
import com.ARD.eCommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "orderItem",target = "orderItem")
    @Mapping(source = "orderStatus",target = "orderStatus")
    @Mapping(source = "user.id",target = "userId")
    public OrderDto toOrderDto(Order order);

    @Mapping(source = "orderItem",target = "orderItem")
    @Mapping(source = "orderStatus",target = "orderStatus")
    public List<OrderDto> toListOrderDtos(List<Order> orders);

    @Mapping(source = "product.id",target = "productId")
    @Mapping(source = "product.name",target = "productName")
    @Mapping(source = "product.brand",target = "productBrand")
    public OrderItemDto toOrderItemDto(OrderItem orderItem);
}
