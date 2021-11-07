package com.tul.challenge.shopping.cart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tul.challenge.shopping.cart.exceptions.cart.item.DuplicateCartItemException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartEmptyException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartHasStateCompletedException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartNotHaveCartItemException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.bind.annotation.PostMapping;

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

    @OneToMany(
            mappedBy = "shoppingCart",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<@Valid CartItem> cartItems;

    @Transient
    private BigDecimal totalAmount;

    @Column(columnDefinition = "ENUM('PENDING', 'COMPLETED')")
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    public ShoppingCart(UUID id) {
        this.id = id;
    }



    public ShoppingCart(Set<@Valid CartItem> cartItems, State state) {
        this.id = UUID.randomUUID();
        this.cartItems = cartItems;
        this.state = state;
        totalAmount();
    }

    public ShoppingCart(Set<@Valid CartItem> cartItems) {
        this.id = UUID.randomUUID();
        this.state = State.PENDING;
        this.cartItems = cartItems;
        totalAmount();
    }

    @PostLoad
    @PostUpdate
    @PostMapping
    @PostPersist
    public void totalAmount(){
        if(cartItems == null){
            this.totalAmount = BigDecimal.ZERO;
            return;
        }
        this.totalAmount = cartItems.stream().map(CartItem::getTotalAmountInCartItem).reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    public boolean addCartItem(CartItem cartItem){
        boolean response = this.cartItems.add(cartItem);
        if(!response) throw new DuplicateCartItemException("Add CartItem on ShoppingCart: Shopping cart already have cart item requested");

        totalAmount();
        return response;
    }

    public void removeCartItem(CartItem cartItem){
        boolean response = cartItems.remove(cartItem);

        if(!response) throw new ShoppingCartNotHaveCartItemException("Remove CartItem on ShoppingCart: Shopping cart not have cart item requested");

        totalAmount();
    }

    public void updateCartItem(CartItem cartItemRequest) {
        boolean hasCartItem = cartItems.remove(cartItemRequest);

        if(!hasCartItem)
        throw new ShoppingCartNotHaveCartItemException("Update CartItem on ShoppingCart: Shopping cart not have cart item requested");

        cartItems.add(cartItemRequest);
        totalAmount();
    }

    public void setCartItems( Set<@Valid CartItem> cartItems) {
        this.cartItems = cartItems;
        totalAmount();
    }

    public void validateShoppingCartNotEmpty() {
        if( this.getCartItems().size() == 0)
            throw new ShoppingCartEmptyException("Shopping cart is empty, not have cart items");
    }

    public void shoppingCartStateIsCompleted() {
        if (this.getState() == State.COMPLETED)
            throw new ShoppingCartHasStateCompletedException("Shopping cart is currently completed");
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ShoppingCart)) return false;

        return this.id.equals(((ShoppingCart) obj).id);
    }
}
