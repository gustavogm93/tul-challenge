package com.tul.challenge.shopping.cart.controller;

import com.tul.challenge.config.exception.error.FormatMessage;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/cart")
public class CartItemController {

    CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(){

        List<CartItem> cartItems = cartItemService.listAllCartItem();

        if (cartItems.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCartItem(@PathVariable("id") UUID id) {
        CartItem cartItem =  cartItemService.getCartItem(id);
        if (cartItem == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart Item not found");
        }
        return ResponseEntity.ok(cartItem);
    }

    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@Valid @RequestBody CartItem cartItem, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatMessage.toJson(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.createCartItem(cartItem));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable("id") UUID id, @Valid @RequestBody CartItem cartItem){
        CartItem cartItemDB =  cartItemService.updateCartItem(id, cartItem);

        return ResponseEntity.ok(cartItemDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("id") UUID id){
        if (cartItemService.deleteCartItem(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("CartItem deleted");
    }


}
