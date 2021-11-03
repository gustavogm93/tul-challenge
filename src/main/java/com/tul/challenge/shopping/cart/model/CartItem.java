package com.tul.challenge.shopping.cart.model;


import com.tul.challenge.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Cart_Item")
@Data
@AllArgsConstructor
public class CartItem implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "PRODUCT_FK"))
    private Product product;

    @NotNull(message = "quantity cannot be empty")
    @Positive
    private int quantity = 1;

    @Column(columnDefinition = "ENUM('PENDING', 'COMPLETED')")
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    /*@Column(name = "shopping_cart_id")
    @Type(type="uuid-char")
    private UUID shoppingCartId; */

    public CartItem(){
        this.id = UUID.randomUUID();
    }

    public static CartItem build(){
        return new CartItem();
    }

    public void updateCartItem(CartItem cartItem){
        this.product = cartItem.product;
        this.quantity = cartItem.quantity;
        this.state = cartItem.state;
    }

}
