package com.tul.challenge.shopping.cart.controller;

import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.services.CartItemService;
import com.tul.challenge.shopping.cart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/shopping")
public class ShoppingController {


    ShoppingCartService shoppingCartService;

    CartItemService cartItemService;

    @Autowired
    public ShoppingController(ShoppingCartService shoppingCartService, CartItemService cartItemService){
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<?> listShoppingCart(){

        List<ShoppingCart> shoppingCartList = shoppingCartService.listAllShoppingCart();

        if (shoppingCartList.isEmpty()) {
            return ResponseEntity.ok().body("There are no shopping carts here");
        }

        return ResponseEntity.ok(shoppingCartList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable("id") UUID id){

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);

        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/create")
    public ResponseEntity<ShoppingCart> createShoppingCart(){

        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart();

        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ShoppingCart> addCartItemToShoppingCart(@PathVariable("id") UUID id, @Valid @RequestBody CartItem cartItem){

        ShoppingCart shoppingCart = shoppingCartService.addCartItemInShoppingCart(id, cartItem);

        return ResponseEntity.ok(shoppingCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItemToShoppingCart(@PathVariable("id") UUID id, @Valid @RequestBody CartItem cartItem){

        shoppingCartService.deleteCartItemInShoppingCart(id, cartItem);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Cart item was successfully removed from shopping cart");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCartItemToShoppingCart(@PathVariable("id") UUID id, @Valid @RequestBody CartItem cartItem){

        shoppingCartService.updateCartItemInShoppingCart(id, cartItem);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Cart item was successfully updated from shopping cart");
    }


    @PostMapping("/checkout/{id}")
    public ResponseEntity<String> checkoutShoppingCart(@PathVariable("id") UUID id){

        BigDecimal totalAmount = shoppingCartService.checkoutShoppingCart(id);
        return ResponseEntity.status(HttpStatus.OK).body(MessageFormat.format("TotalAmount: {0}.", totalAmount));
    }



}
