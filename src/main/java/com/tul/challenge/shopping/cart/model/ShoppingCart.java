package com.tul.challenge.shopping.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
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
    private Set<@Valid CartItem> cartItems;

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

    @PostLoad
    @PostUpdate
    @PostRemove
    public void TotalPrice(){
        this.totalPrice = cartItems.stream().map(CartItem::getProductPrice).reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    public boolean removeCartItem(CartItem cartItemId){
        return cartItems.remove(cartItemId);
    }

    public boolean updateCartItem(CartItem cartItemId) {
        cartItems.remove(cartItemId);
        return cartItems.add(cartItemId);
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ShoppingCart)) return false;

        return this.id.equals(((ShoppingCart) obj).id);
    }
}
