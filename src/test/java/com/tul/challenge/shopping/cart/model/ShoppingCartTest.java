package com.tul.challenge.shopping.cart.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static com.tul.challenge.shopping.cart.model.CartItemTest.getSetOfCartItemsMock;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ShoppingCartTest {

    private ShoppingCart shoppingCartMock;

    @BeforeEach()
    public void setUp(){
        shoppingCartMock = getShoppingCartMock();
    }

    @Test
    @DisplayName("Get Total Amount of shopping cart")
    public void testTotalAmount(){
        assertThat(shoppingCartMock.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(300.00));
    }

    @Test
    @DisplayName("On Update Cart Item, Total Amount of shopping cart must change")
    public void testUpdateCartItemInShoppingCart(){

        BigDecimal oldTotalAmount = shoppingCartMock.getTotalAmount();

        CartItem cartItem = shoppingCartMock.getCartItems().stream().findFirst().orElse(null);
        BigDecimal totalAmountInCart = cartItem.getTotalAmountInCartItem();

        shoppingCartMock.removeCartItem(cartItem);

        assertThat(shoppingCartMock.getTotalAmount()).isEqualByComparingTo(oldTotalAmount.subtract(totalAmountInCart));
    }

    @Test
    @DisplayName("Get Correct Total Amount of shopping cart when CartItems was removed recently")
    public void testTotalAmountOnInvalidShoppingCart(){

        ShoppingCart shoppingCartMock = getShoppingCartMock();
        shoppingCartMock.setCartItems(null);

        assertThat(shoppingCartMock.getTotalAmount()).isEqualByComparingTo(BigDecimal.ONE);
    }

    public ShoppingCart getShoppingCartMock(){
        return new ShoppingCart(UUID.randomUUID(), getSetOfCartItemsMock(), State.PENDING);
    }



}
