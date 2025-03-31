package com.ARD.eCommerce.services.cart;

import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.CartMapper;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.repository.CartItemRepo;
import com.ARD.eCommerce.repository.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    private final CartMapper cartMapper;


    private final AtomicLong counter = new AtomicLong(0);
    @Override
    public Cart getCartById(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);
//        Cart savedCart = cartRepo.save(cart);
//        return cartMapper.CartToDto(savedCart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
//        cartItemRepo.deleteAllByCartId(id);
//        cart.getItems().clear();
        cartRepo.deleteCartItem(cart.getId());
        cartRepo.deleteCartById(id);

    }

    @Override
    public BigDecimal getTotalBrice(Long id) {
//        CartDto cartDto = getCartById(id);
//        Cart cart = cartMapper.cartDtoToCart(cartDto);
        Cart cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    public boolean existsById(Long cartId) {
        return cartRepo.existsById(cartId);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }

    @Override
    public Cart initializeNewCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepo.save(cart);
                });
    }
}
















