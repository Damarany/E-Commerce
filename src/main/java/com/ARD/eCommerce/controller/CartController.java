package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.CartDto;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.CartMapper;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/carts")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ResponseAPI>getCartById(@PathVariable Long cartId){
        try {

            Cart cart = cartService.getCartById(cartId);
            CartDto cartDto = cartMapper.CartToDto(cart);
            return ResponseEntity.ok(new ResponseAPI("success",cartDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ResponseAPI>clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ResponseAPI("clear cart done successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(),null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ResponseAPI>getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal totalBrice = cartService.getTotalBrice(cartId);
            return ResponseEntity.ok(new ResponseAPI("ToTal Amount is: "+totalBrice , totalBrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(),null));
        }
    }




}













