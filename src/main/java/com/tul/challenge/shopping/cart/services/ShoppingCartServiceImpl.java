package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.config.exception.DuplicateCartItemException;
import com.tul.challenge.config.exception.NotFoundException;
import com.tul.challenge.config.exception.ShoppingCartEmptyException;
import com.tul.challenge.config.exception.ShoppingCartHasStateCompletedException;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.model.State;
import com.tul.challenge.shopping.cart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public ShoppingCart createShoppingCart() {
        return shoppingCartRepository.save(new ShoppingCart(UUID.randomUUID()));
    }

    @Override
    public ShoppingCart addCartItemInShoppingCart(UUID shoppingCartId, CartItem cartItem) {

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

    public BigDecimal checkoutShoppingCart(UUID shoppingCartId) {

        ShoppingCart shoppingCart = this.getShoppingCart(shoppingCartId);

        if (shoppingCart == null)
            throw new NotFoundException("shopping cart not found");

        if (shoppingCart.getState() == State.COMPLETED)
            throw new ShoppingCartHasStateCompletedException("shopping cart is currently completed");

        if( shoppingCart.getCartItems() == null)
            throw new ShoppingCartEmptyException("shopping cart is empty");

        shoppingCart.setState(State.COMPLETED);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart.getTotalPrice();
    }


    public boolean deleteCartItemInShoppingCart(UUID id, CartItem cartItem) {

        ShoppingCart shoppingCart = this.getShoppingCart(id);
        CartItem cartItemDB = cartItemService.getCartItem(cartItem.getId());

        if (cartItemDB == null)
            throw new NotFoundException("cart item not found");

        if (shoppingCart == null)
            throw new NotFoundException("shopping Cart not found");

        if(!shoppingCart.removeCartItem(cartItemDB))
            throw new NotFoundException("Shopping cart doesn't have cart item");

        shoppingCartRepository.save(shoppingCart);

        return true;

    }


    public boolean updateCartItemInShoppingCart(UUID id, CartItem cartItem) {

        ShoppingCart shoppingCart = this.getShoppingCart(id);
        CartItem cartItemDB = cartItemService.getCartItem(cartItem.getId());

        if (cartItemDB == null)
            throw new NotFoundException("cart item not found");

        if (shoppingCart == null)
            throw new NotFoundException("shopping Cart not found");

        if(!shoppingCart.updateCartItem(cartItemDB))
            throw new NotFoundException("Shopping cart doesn't have cart item");

        shoppingCartRepository.save(shoppingCart);

        return true;
    }

}