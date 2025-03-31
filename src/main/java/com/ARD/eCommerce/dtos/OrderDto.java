package com.ARD.eCommerce.dtos;

import com.ARD.eCommerce.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {

    private Long Id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;
    private List<OrderItemDto> orderItem;

}

