package com.tul.challenge.product.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "Product")
public class Product implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @NotEmpty(message = "name cannot not be empty")
    @Column(name="name")
    private String name;

    @Digits(integer = 6, fraction = 2, message = "price must be 2 fraction decimal")
    @Positive(message = "price must be positive")
    private BigDecimal price;

    @NotEmpty(message = "SKU cannot be empty")
    private String SKU;

    @NotEmpty(message = "description cannot be empty")
    private String description;

    private boolean discount = false;

    protected Product(){
        this.id = UUID.randomUUID();
    }

    public static Product build(){
        return new Product();
    }

    public Product fill(String name, BigDecimal price, String description, String sku, boolean discount) {
        price = discount ? price.divide(BigDecimal.valueOf(2)) : price;

         Product product = new Product();
            product.setName(name);
            product.setSKU(sku);
            product.setDescription(description);
            product.setPrice(price);
            product.setDiscount(discount);

            return product;
    }

/*

BRAND - TIPO DE PRODUCTO -
    public Product(String name, String sku, String description,Double price, boolean discount) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.price = discount ? price / 2.00 : price;
        this.discount = discount;
    } */

//EN LA BASE DE DATOS SIEMPRE EL PUBLIC CON TODO
  // CUANDO LO CREO YO

    //get traigo un objeto ya listo
    // construyo y se reduce el precio

/*
  public static Product createProduct(){
        return Product.builder().name("Pala").sku("a").description("pala ").price(250.00).build();
  }

  public static Product createDiscountedProduct(){
      return Product.builder().name("Pala").sku("a").description("pala ").price(450.00).discount(true).build();
  }
*/

}
