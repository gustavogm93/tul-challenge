package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.config.exception.CartItemNotFoundException;
import com.tul.challenge.config.exception.ProductNotFoundException;
import com.tul.challenge.product.model.Product;
import com.tul.challenge.product.services.ProductService;
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
    private final ProductService productService;

    public List<CartItem> listAllCartItem() {
        return cartItemRepository.findAll();
    }

    public CartItem getCartItem(UUID id)  {
        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(UUID id, CartItem cartItemRequest) {

        CartItem cartItemDB = getCartItem(id);
        if (null == cartItemDB){
            throw new CartItemNotFoundException("cart item not found");
        }
        Product productDB = productService.getProduct(cartItemRequest.getProduct().getId());
        if (null == productDB){
            throw new ProductNotFoundException("Product not found");
        }
        cartItemRequest.setProduct(productDB);

        cartItemDB.updateCartItem(cartItemRequest);

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
