package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.config.exception.DuplicateCartItemException;
import com.tul.challenge.config.exception.NotFoundException;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

    private final ShoppingCartRepository shoppingCartRepository;

    private final CartItemService cartItemService;

    public List<ShoppingCart> listAllShoppingCart() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getShoppingCart(UUID id)  {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }


    @Override
    public ShoppingCart addCartItem(UUID shoppingCartId, CartItem cartItem) {

        ShoppingCart shoppingCart = this.getShoppingCart(shoppingCartId);
        CartItem cartItemDB = cartItemService.getCartItem(cartItem.getId());

        if (cartItemDB == null)
            throw new NotFoundException("cart item not found");

        if (shoppingCart == null)
            throw new NotFoundException("shopping Cart not found");

        if(!shoppingCart.addCartItem(cartItemDB))
            throw new DuplicateCartItemException("duplicate cart item");

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

    @Override
    public boolean deleteShoppingCart(UUID id) {
        return false;
    }

}
