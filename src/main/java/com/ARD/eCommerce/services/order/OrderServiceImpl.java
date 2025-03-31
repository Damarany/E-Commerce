package com.ARD.eCommerce.services.order;

import com.ARD.eCommerce.dtos.OrderDto;
import com.ARD.eCommerce.enums.OrderStatus;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.OrderMapper;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.Order;
import com.ARD.eCommerce.model.OrderItem;
import com.ARD.eCommerce.model.Product;
import com.ARD.eCommerce.repository.OrderRepo;
import com.ARD.eCommerce.repository.ProductRepo;
import com.ARD.eCommerce.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    private final CartService cartService;

    private final OrderMapper orderMapper;
    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItem(order,cart);

        order.setOrderItem(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Order savedOrder = orderRepo.save(order);

        cartService.clearCart(cart.getId());
        return orderMapper.toOrderDto(savedOrder);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("this order ID not found"));
        return orderMapper.toOrderDto(order);
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        return order;
    }

    private List<OrderItem> createOrderItem(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepo.save(product);
            return new OrderItem(cartItem.getQuantity(),cartItem.getUnitPrice(),order,product);
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        return orderItems.stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepo.findByUserId(userId);
        return orderMapper.toListOrderDtos(orders);
    }
}

















