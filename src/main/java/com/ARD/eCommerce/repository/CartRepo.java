package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);


    @Modifying
    @Query("delete from CartItem ci where ci.cart.id = :id")
    void deleteCartItem(Long id);
    @Modifying
    @Query("delete from Cart c where c.id = :id")
    void deleteCartById(Long id);
}
