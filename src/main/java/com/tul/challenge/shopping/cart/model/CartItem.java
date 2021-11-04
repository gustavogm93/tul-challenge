package com.tul.challenge.shopping.cart.model;


import com.tul.challenge.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "Cart_Item")
@Data
@AllArgsConstructor
public class CartItem implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "PRODUCT_FK"))
    private Product product;

    @NotNull(message = "quantity cannot be empty")
    @Positive
    private int quantity = 1;

    public CartItem(){
        this.id = UUID.randomUUID();
    }

    public static CartItem build(){
        return new CartItem();
    }

    public void updateCartItem(CartItem cartItem){
        this.product = cartItem.product;
        this.quantity = cartItem.quantity;
    }

    public BigDecimal getProductPrice(){
        return this.product.getPrice();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CartItem)) return false;

        return this.id.equals(((CartItem) obj).id);
    }
}
