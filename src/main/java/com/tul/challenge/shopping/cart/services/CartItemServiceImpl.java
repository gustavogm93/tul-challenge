package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.product.model.Product;
import com.tul.challenge.product.repository.ProductRepository;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> listAllCartItem() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItem(UUID id)  {
        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        CartItem cartItemDB = getCartItem(cartItem.getId());
        if (null == cartItemDB){
            return null;
        }
        cartItemDB.updateCartItem(cartItem);

        return cartItemRepository.save(cartItemDB);
    }

    public boolean deleteCartItem(UUID id) {
        CartItem cartItemDB = getCartItem(id);
        if (null == cartItemDB){
            return false;
        }
        cartItemRepository.delete(cartItemDB);
        return true;
    }
}
