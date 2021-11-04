package com.tul.challenge.shopping.cart.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.tul.challenge.product.model.ProductTest.getProductByPriceMock;
import static com.tul.challenge.product.model.ProductTest.getProductMock;

public class CartItemTest {

    private final static BigDecimal productPrice = BigDecimal.valueOf(100.00);

    public static CartItem getCartItemMock(){
        return CartItem.builder().id(UUID.randomUUID()).quantity(1).product(getProductByPriceMock(productPrice)).build();
    }

    public static CartItem getCartItemTwoUnitsMock(){
        return CartItem.builder().id(UUID.randomUUID()).quantity(2).product(getProductByPriceMock(productPrice)).build();
    }

    public static Set<CartItem> getSetOfCartItemsMock(){
        Set<CartItem> cartItemSet = new HashSet<>();
        cartItemSet.add(getCartItemMock());
        cartItemSet.add(getCartItemTwoUnitsMock());

        return cartItemSet;
    }
}
