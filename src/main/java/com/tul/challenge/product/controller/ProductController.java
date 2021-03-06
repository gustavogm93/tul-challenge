package com.tul.challenge.product.controller;

import com.tul.challenge.config.exception.error.FormatMessage;
import com.tul.challenge.product.model.Product;
import com.tul.challenge.product.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/product")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> listProduct(){

        List<Product> products = productService.listAllProduct();

        if (products.isEmpty()){
            return ResponseEntity.ok().body("There are no products here");
        }
        return ResponseEntity.ok(products);
        }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") UUID id) {
        Product product =  productService.getProduct(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatMessage.toJson(result));
        }
        Product productCreate =  productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") UUID id, @Valid @RequestBody Product productRequest, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FormatMessage.toJson(result));
        }

        Product productDB =  productService.updateProduct(id, productRequest);

        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID id){

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted");
    }


}
