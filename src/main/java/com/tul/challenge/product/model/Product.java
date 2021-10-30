package com.tul.challenge.product.model;

import com.tul.challenge.config.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Table(name = "Product")
@Data
public class Product {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    private String name;

    private BigDecimal price;

    private String sku;

    private String description;

    private boolean discount = false;

    protected Product(){
        this.id = UUID.randomUUID();
    }

    public Product fill(String name, BigDecimal price, String description, String sku, boolean discount) {
        price = discount ? price.divide(BigDecimal.valueOf(2)) : price;

         Product product = new Product();
            product.setName(name);
            product.setSku(sku);
            product.setDescription(description);
            product.setPrice(price);
            product.setDiscount(discount);

            validateProduct();
            return product;
    }


    public static Product build(){
        return new Product();
    }

     public void validateProduct() {

        if(this.name.isEmpty())
            throw new BadRequestException("hola");
    }


/*
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
