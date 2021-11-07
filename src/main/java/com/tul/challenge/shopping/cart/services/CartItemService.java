package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.shopping.cart.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartItemService {

    public List<CartItem> listAllCartItem();
    public CartItem getCartItem(UUID id);
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(UUID id, CartItem cartItem);
    public boolean deleteCartItem(UUID id);
    public boolean deleteByShoppingCartId(UUID id);
}