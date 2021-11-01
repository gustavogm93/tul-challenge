package com.tul.challenge.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Product")
public class Product implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID id = UUID.randomUUID();

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

    public void updateProduct(Product product) {
        BigDecimal oldPrice = this.price;

        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.SKU = product.getSKU();
        this.updateDiscount(product.isDiscount(), oldPrice);
    }

    private void updateDiscount(boolean discount, BigDecimal oldPrice){
        if(discount == this.discount || this.price.compareTo(oldPrice) != 0) return;
        //price distinct no update
        this.price =  this.discount ? this.price.multiply(BigDecimal.valueOf(2)) : this.price.divide(BigDecimal.valueOf(2));

        this.discount = discount;
    }
}
