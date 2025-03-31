package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
