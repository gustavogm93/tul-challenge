package com.tul.challenge.product.infrastructure;

import com.tul.challenge.shopping.cart.model.ShoppingCart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/")
    public ShoppingCart home(){
        ShoppingCart cart = new ShoppingCart();
        return cart;
    }


}
