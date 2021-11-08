package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.config.exception.CustomNotFoundException;
import com.tul.challenge.product.model.Product;
import com.tul.challenge.product.services.ProductService;
import com.tul.challenge.shopping.cart.exceptions.cart.item.AnotherCartItemHasTheSameProductException;
import com.tul.challenge.shopping.cart.exceptions.cart.item.DuplicateCartItemException;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return cartItemRepository.findById(id).orElseThrow(()-> new CustomNotFoundException(CartItem.class));
    }

    public CartItem createCartItem(CartItem cartItem) {

            //TODO: ver si es necesario, cuando hace el save genera un id diferente, el id no tenerlo en cuenta
            Optional<CartItem> cartItemRepeated = cartItemRepository.findById(cartItem.getId());
            if (cartItemRepeated.isPresent())
                throw new DuplicateCartItemException(String.format("CartItem with id %s already exists", cartItem.getId()));

            Product productDB = productService.getProduct(cartItem.getProduct().getId());

            if(cartItemRepository.getCartItemByProductId(productDB.getId()) != null)
            throw new AnotherCartItemHasTheSameProductException();

            cartItem.setProduct(productDB);

            return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(UUID id, CartItem cartItemRequest) {

            CartItem cartItemDB = getCartItem(id);

            Product productDB = productService.getProduct(cartItemRequest.getProduct().getId());

            CartItem cartItemWithSameProduct = cartItemRepository.getCartItemByProductId(productDB.getId());

            if(cartItemWithSameProduct.getId() != cartItemDB.getId())
            throw new AnotherCartItemHasTheSameProductException();

            cartItemRequest.setProduct(productDB);

            cartItemDB.updateCartItem(cartItemRequest);

            return cartItemRepository.save(cartItemDB);

    }

    public boolean deleteCartItem(UUID id) {
        CartItem cartItemDB = getCartItem(id);

        cartItemRepository.delete(cartItemDB);
        return true;
    }

    public boolean deleteByShoppingCartId(UUID shoppingCartId){
        cartItemRepository.deleteCartItemsByShoppingCartId(shoppingCartId);
        return true;
    }
}
