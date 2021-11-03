package com.tul.challenge.shopping.cart.model;

import com.tul.challenge.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "Shopping_Cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart implements Serializable {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @OneToMany(targetEntity = CartItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id")
    private Set<CartItem> cartItems;

    @Transient
    private BigDecimal totalPrice;

    @Column(columnDefinition = "ENUM('PENDING', 'COMPLETED')")
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    public ShoppingCart(UUID id) {
        this.id = id;
    }

    public boolean addCartItem(CartItem cartItem){
        return this.cartItems.add(cartItem);
    }


}
