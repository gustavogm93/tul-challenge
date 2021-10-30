package com.tul.challenge.product.controller;

import com.tul.challenge.product.model.Product;
import com.tul.challenge.product.services.ProductService;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/")
    public Product home(){
        Product product = Product.build().fill("Pintura", BigDecimal.valueOf(200.10) ,"PRA-52", "pintura en balde", true);

        return product;
    }


}
