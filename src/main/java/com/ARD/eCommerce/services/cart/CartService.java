package com.ARD.eCommerce.services.cart;

import com.ARD.eCommerce.dtos.CartDto;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCartById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalBrice(Long id);
    boolean existsById(Long cartId);

    Cart getCartByUserId(Long userId);

    Cart initializeNewCart(User user);
}
