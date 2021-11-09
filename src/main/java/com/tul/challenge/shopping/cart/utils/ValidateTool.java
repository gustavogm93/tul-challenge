package com.tul.challenge.shopping.cart.utils;

import com.tul.challenge.config.exception.NotFoundException;
import com.tul.challenge.shopping.cart.exceptions.shopping.cart.ShoppingCartHasStateCompletedException;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.model.State;

public class ValidateTool {




    public static void cartItemIsNull(CartItem cartItem) {
        if (cartItem == null)
            throw new NotFoundException("cart item not found");
    }



}
