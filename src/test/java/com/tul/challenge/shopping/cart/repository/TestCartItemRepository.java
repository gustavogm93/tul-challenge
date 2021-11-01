package com.tul.challenge.shopping.cart.repository;

import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.repository.CartItemRepository;
import com.tul.challenge.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestCartItemRepository {

    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private TestEntityManager testEntityManager;

/*
    @BeforeEach
    public void setUp(){

    }*/

    @Test
    public void testSaveItem() {
        UUID productId = UUID.randomUUID();;
        //testEntityManager.persist(Product.build().fill("Pala", BigDecimal.valueOf(500.30, "pala nueva", "SKU-2", true));

        Product product = testEntityManager.find(Product.class, productId);

        CartItem cartItemMock = new CartItem();
        cartItemMock.setProduct(product);
        cartItemMock.setQuantity(1);

        CartItem cartItem = cartItemRepository.save(cartItemMock);
        System.out.println(cartItem);
    }

}
