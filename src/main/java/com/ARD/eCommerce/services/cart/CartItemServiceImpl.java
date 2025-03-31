package com.ARD.eCommerce.services.cart;

import com.ARD.eCommerce.dtos.CartDto;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.CartItemMapper;
import com.ARD.eCommerce.mapper.CartMapper;
import com.ARD.eCommerce.model.Cart;
import com.ARD.eCommerce.model.CartItem;
import com.ARD.eCommerce.model.Product;
import com.ARD.eCommerce.repository.CartItemRepo;
import com.ARD.eCommerce.repository.CartRepo;
import com.ARD.eCommerce.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepo cartItemRepo;
    private final ProductService productService;
    private final CartRepo cartRepo;
    private final CartService cartService;

    private final CartMapper cartMapper;

    private final CartItemMapper cartItemMapper;





    @Transactional
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //get the cart
        //get the product
        //check if product already in the cart
        //if yes, increase the quantity to required quantity
        //if no,initiate new item to the cart
//        if (!cartService.existsById(cartId)){
//            Cart cart = new Cart();
//            cartRepo.save(cart);
//            System.out.println("CART NOT EXIST!!!!!!!!!!!!!!!!!!!!");
//        }
//        CartDto cartDto = cartService.getCartById(cartId);
//        System.out.println(cartDto.getItems() + " iiiiiiiiiiiiiiiiiitems");
//        Cart cart = cartMapper.cartDtoToCart(cartDto);
        Cart cart = cartService.getCartById(cartId);
        System.out.println(cart.getId() + " firstCARTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println(cart.getItems().stream().map(cartItem -> cartItem.getProduct().getId()).toList() + "iiiiidddd");
        Product product  = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(product.getInventory() > 0 && product.getInventory() >= quantity){
            if (cartItem.getId() == null){
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(product.getPrice());
            }else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
        }else{
            throw new ResourceNotFoundException("this quantity not available in stock");
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepo.save(cartItem);
        cartRepo.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
//        CartDto cartDto = cartService.getCartById(cartId);
//        Cart cart = cartMapper.cartDtoToCart(cartDto);
        Cart cart = cartService.getCartById(cartId);
        System.out.println("CART  "+ cart.getItems());
        CartItem cartItem = getCartItem(cartId,productId);
        cart.removeItem(cartItem);
        cartRepo.save(cart);
    }

    @Override
    public void updateItemQuatity(Long cartId, Long productId, int quantity) {
//        CartDto cartDto = cartService.getCartById(cartId);
//        Cart cart = cartMapper.cartDtoToCart(cartDto);
        Cart cart = cartService.getCartById(cartId);
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.getTotalPrice();
                });

        BigDecimal totalAmount = cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        cart.setTotalAmount(totalAmount);

        for (CartItem item : cart.getItems()) {
            System.out.println("Product: " + item.getProduct());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("id: " + item.getId());
            // You can perform any other operations here
        }
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
//        CartDto cartDto = cartService.getCartById(cartId);
//        Cart cart = cartMapper.cartDtoToCart(cartDto);
        Cart cart = cartService.getCartById(cartId);
        return  cart.getItems().stream().
                filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Item not found!"));
    }


}
