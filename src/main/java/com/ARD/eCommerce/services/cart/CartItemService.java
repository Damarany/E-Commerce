package com.ARD.eCommerce.services.cart;

import com.ARD.eCommerce.model.CartItem;

import java.util.Set;

public interface CartItemService {
   void addItemToCart(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId,Long productId);

    void updateItemQuatity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
