package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ShoppingCartService {

    public List<ShoppingCart> listAllShoppingCart();
    public ShoppingCart getShoppingCart(UUID id);
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart);
    public ShoppingCart createShoppingCart();
    public ShoppingCart addCartItemInShoppingCart(UUID shoppingCart, CartItem cartItem);
    public boolean deleteCartItemInShoppingCart(UUID id, CartItem cartItem);
    public boolean updateCartItemInShoppingCart(UUID id, Set<CartItem> cartItem);
    public BigDecimal checkoutShoppingCart(UUID id);

}
