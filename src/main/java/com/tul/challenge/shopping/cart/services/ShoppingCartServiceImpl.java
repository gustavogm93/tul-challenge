package com.tul.challenge.shopping.cart.services;

import com.tul.challenge.config.exception.CustomNotFoundException;
import com.tul.challenge.shopping.cart.exceptions.cart.item.SomeCartItemNotExist;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.model.State;
import com.tul.challenge.shopping.cart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService{

    private final ShoppingCartRepository shoppingCartRepository;

    private final CartItemService cartItemService;

    public List<ShoppingCart> listAllShoppingCart() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getShoppingCart(UUID id)  {
        return shoppingCartRepository.findById(id).orElseThrow(()-> new CustomNotFoundException(ShoppingCart.class));
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

        cartItemDB.setShoppingCart(shoppingCart);

        shoppingCart.addCartItem(cartItemDB);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }

    @Transactional
    public BigDecimal checkoutShoppingCart(UUID shoppingCartId) {

        ShoppingCart shoppingCart = this.getShoppingCart(shoppingCartId);

        shoppingCart.shoppingCartStateIsCompleted();

        shoppingCart.validateShoppingCartNotEmpty();

        shoppingCart.setState(State.COMPLETED);
        BigDecimal totalAmount = shoppingCart.getTotalAmount();

        cartItemService.deleteByShoppingCartId(shoppingCart.getId());

        shoppingCartRepository.save(shoppingCart);

        return totalAmount;
    }


    public boolean deleteCartItemInShoppingCart(UUID id, CartItem cartItem) {
        ShoppingCart shoppingCart = this.getShoppingCart(id);

        CartItem cartItemDB = cartItemService.getCartItem(cartItem.getId());

        shoppingCart.removeCartItem(cartItemDB);

        shoppingCartRepository.saveAndFlush(shoppingCart);

        return true;
    }

    //improve with List to List relationship, grouping by id and updating every cart.
    public boolean updateCartItemInShoppingCart(UUID id, Set<CartItem> cartItemSet) {

        List<UUID> ids = cartItemSet.stream().map(CartItem::getId).collect(Collectors.toList());

        Set<CartItem> cartItemsDB = cartItemService.getCartItemsById(ids);

        //TODO: improve this because can not exist some cart item
        if(cartItemsDB.size() != cartItemSet.size()){
            throw new SomeCartItemNotExist();
        }

        ShoppingCart shoppingCart = this.getShoppingCart(id);

        //Sometimes cart item wasn't assigned it shopping cart id.
        cartItemSet.forEach(cartItem -> cartItem.setShoppingCart(shoppingCart));

        shoppingCart.setCartItems(cartItemSet);

        shoppingCartRepository.save(shoppingCart);

        return true;
    }

}
