package com.tul.challenge.shopping.cart.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tul.challenge.product.model.Product;
import com.tul.challenge.shopping.cart.exceptions.cart.item.UpdateDifferentCartItemException;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
public class CartItem implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "PRODUCT_FK"), unique = true)
    private Product product;

    @NotNull
    @Positive
    private int quantity = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id" , referencedColumnName = "id", nullable = true)
    @JsonBackReference
    private ShoppingCart shoppingCart;


    public CartItem(){
        this.id = UUID.randomUUID();
    }

    public void updateCartItem(CartItem cartItemRequest){
        if(this.id.compareTo(cartItemRequest.getId()) != 0)
            throw new UpdateDifferentCartItemException("Your Cart item Id Path is different from cart item id request body");

        this.product = cartItemRequest.product;
        this.quantity = cartItemRequest.quantity;
    }

    @Transient
    public BigDecimal getTotalAmountInCartItem(){
        if(this.product == null) return BigDecimal.ONE;

        return this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof CartItem)) return false;

        return this.id.equals(((CartItem) obj).id);
    }
}
