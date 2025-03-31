package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.repository.CartRepo;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.User.UserService;
import com.ARD.eCommerce.services.cart.CartItemService;
import com.ARD.eCommerce.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;


    @PostMapping("/item/add")
    public ResponseEntity<ResponseAPI>addItemToCart(
                                                    @RequestParam Long productId,
                                                   @RequestParam int quantity){
        try {
//            if (cartId == null){
//                Cart cart = new Cart();
//                cart.setId(cartId);
//
////                cart.setItems(cartItemService.addItemToCart(cartId,productId,quantity));
//                cartRepo.save(cart);
//            }
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(), productId,quantity);
            return ResponseEntity.ok(new ResponseAPI("Item added successfully!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(),null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ResponseAPI>removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ResponseAPI("Item deleted successfully!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(),null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ResponseAPI>updateItemQuantity(@PathVariable Long cartId,
                                                         @PathVariable Long productId,
                                                         @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuatity(cartId,productId,quantity);
            return ResponseEntity.ok(new ResponseAPI("Item quantity updated successfully!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(),null));
        }

    }
}










