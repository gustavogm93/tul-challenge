package com.tul.challenge.shopping.cart.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestCartItemRepository {

    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void injectedComponentsAreNotNull(){
        Assertions.assertNotNull(cartItemRepository);
        Assertions.assertNotNull(testEntityManager);
    }


    private final UUID productId = UUID.randomUUID();

}
