package com.tul.challenge.shopping.cart.repository;

import com.tul.challenge.product.model.Product;
import com.tul.challenge.shopping.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByProduct(Product product);

    @Query("SELECT ci FROM CartItem ci WHERE ci.product.id =:productId")
    Optional<CartItem> getCartItemByProductId(@Param("productId") UUID productId);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.shoppingCart.id =:shoppingId")
    void deleteCartItemsByShoppingCartId(@Param("shoppingId") UUID shoppingId);


    @Query("select ci from CartItem ci where ci.id in :ids" )
    Set<CartItem> findByIds(@Param("ids") List<UUID> postIdsList);
}
