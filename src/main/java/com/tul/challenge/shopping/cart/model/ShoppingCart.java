package com.tul.challenge.shopping.cart.model;

import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartNotHaveCartItemException;
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
    private BigDecimal totalAmount;

    @Column(columnDefinition = "ENUM('PENDING', 'COMPLETED')")
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    public ShoppingCart(UUID id) {
        this.id = id;
    }

    public boolean addCartItem(CartItem cartItem){
        return this.cartItems.add(cartItem);
    }

    public ShoppingCart(UUID id, Set<@Valid CartItem> cartItems, State state) {
        this.id = id;
        this.cartItems = cartItems;
        this.state = state;
        totalAmount();
    }

    @PostLoad
    @PostUpdate
    public void totalAmount(){
        if(cartItems == null){
            this.totalAmount = BigDecimal.ONE;
            return;
        }
        this.totalAmount = cartItems.stream().map(CartItem::getTotalAmountInCartItem).reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    public boolean removeCartItem(CartItem cartItemId){
        boolean response = cartItems.remove(cartItemId);
        totalAmount();

        return response;
    }

    public boolean updateCartItem(CartItem cartItemRequest) {
        boolean hasCartItem = cartItems.remove(cartItemRequest);

        if(!hasCartItem)
        throw new ShoppingCartNotHaveCartItemException("Update CartItem on ShoppingCart: Shopping cart not have cart item requested");

        cartItems.add(cartItemRequest);
        totalAmount();

        return true;
    }

    public void setCartItems( Set<@Valid CartItem> cartItems) {
        this.cartItems = cartItems;
        totalAmount();
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ShoppingCart)) return false;

        return this.id.equals(((ShoppingCart) obj).id);
    }
}
