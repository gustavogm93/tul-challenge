package com.tul.challenge.shopping.cart.controller;

import com.tul.challenge.product.model.Product;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.services.CartItemService;
import com.tul.challenge.shopping.cart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/shopping")
public class ShoppingTemporalController {


    ShoppingCartService shoppingCartService;

    CartItemService cartItemService;

    @Autowired
    public ShoppingTemporalController(ShoppingCartService shoppingCartService, CartItemService cartItemService){
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> listShoppingCart(){

        List<ShoppingCart> shoppingCartList = shoppingCartService.listAllShoppingCart();

        if (shoppingCartList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCartList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable("id") UUID id){

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);

        if (shoppingCart == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ShoppingCart> addCartItemToShoppingCart(@PathVariable("id") UUID id, @Valid @RequestBody CartItem cartItem){

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);
        CartItem cartItemDB = cartItemService.getCartItem(cartItem.getId());

        if (cartItemDB == null)
            return ResponseEntity.notFound().build();

        if (shoppingCart == null)
            return ResponseEntity.notFound().build();

        if(!shoppingCart.addCartItem(cartItemDB))
            throw new D
//CHECKOUT
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<ShoppingCart> addCartItemToShoppingCart(@PathVariable("id") UUID id){


        if (cartItemDB == null)
            return ResponseEntity.notFound().build();

        if (shoppingCart == null)
            return ResponseEntity.notFound().build();

        if(!shoppingCart.addCartItem(cartItemDB))
            throw new D

        return ResponseEntity.ok(shoppingCart);
    }

}
