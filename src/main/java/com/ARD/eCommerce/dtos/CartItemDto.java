package com.ARD.eCommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
//    private Long id;
    private  int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
//    private Long productId;
    private ProductDto product;
//   private CartDto cart;
}
