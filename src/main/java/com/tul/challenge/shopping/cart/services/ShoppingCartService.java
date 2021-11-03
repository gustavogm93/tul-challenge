package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;

import java.util.List;
import java.util.UUID;

public interface ShoppingCartService {

    public List<ShoppingCart> listAllShoppingCart();
    public ShoppingCart getShoppingCart(UUID id);
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart);
    public ShoppingCart addCartItem(UUID shoppingCart, CartItem cartItem);
    public boolean deleteShoppingCart(UUID id);

}
