package com.tul.challenge.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NotEmpty(message = "name cannot be empty")
    @Column(name="name")
    private String name;

    @NotNull(message = "price cannot not be empty")
    @Digits(integer = 6, fraction = 3, message = "price must be 3 fraction decimal")
    @Positive(message = "price must be positive")
    private BigDecimal price;

    @NotEmpty(message = "SKU cannot be empty")
    private String SKU;

    @NotEmpty(message = "description cannot be empty")
    private String description;

    private boolean discount = false;

    public void updateProduct(Product productRequest) {
        BigDecimal oldPrice = this.price;

        this.name = productRequest.getName();
        this.description = productRequest.getDescription();
        this.price = productRequest.getPrice();
        this.SKU = productRequest.getSKU();

        this.updateDiscount(productRequest.isDiscount(), oldPrice);
    }

    private void updateDiscount(boolean discount, BigDecimal oldPrice){
        if(discount == this.discount || this.price.compareTo(oldPrice) != 0) return;
        //price distinct no update
        this.price =  this.discount ? this.price.multiply(BigDecimal.valueOf(2)) : this.price.divide(BigDecimal.valueOf(2));

        this.discount = discount;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Product)) return false;

        return this.id.equals(((Product) obj).id);
    }
}
