package com.tul.challenge.shopping.cart.repository;

import com.tul.challenge.product.model.Product;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, UUID> {

}
