package com.ARD.eCommerce.services.order;

import com.ARD.eCommerce.dtos.OrderDto;
import com.ARD.eCommerce.model.Order;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
