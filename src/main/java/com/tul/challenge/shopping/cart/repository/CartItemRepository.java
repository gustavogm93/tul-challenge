package com.tul.challenge.shopping.cart.repository;

import com.tul.challenge.product.model.Product;
import com.tul.challenge.shopping.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem, UUID> {
    public List<CartItem> findByProduct(Product product);


}
