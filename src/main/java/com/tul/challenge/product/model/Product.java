package com.tul.challenge.product.model;

import com.tul.challenge.shopping.cart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Jacksonized
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

    public Product() {}
    public Product(UUID id) {this.id = id; }

    public Product(UUID uuid, String name, BigDecimal price, String SKU, String description, boolean discount) {
        this.id = uuid;
        this.name = name;
        this.price = price;
        this.SKU = SKU;
        this.description = description;
        this.discount = discount;
    }

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

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Product)) return false;

        return this.id.equals(((Product) obj).id);
    }
}
