package com.tul.challenge.shopping.cart.repository;

import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.product.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
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




    @Test
    public void testSaveCartItem() {

        Product product = testEntityManager.find(Product.class, productId);

        CartItem cartItemMock1 = new CartItem();
        cartItemMock1.setProduct(product);
        cartItemMock1.setQuantity(1);

        CartItem cartItemMock2 = new CartItem();
        cartItemMock2.setProduct(new Product(UUID.randomUUID()));
        cartItemMock2.setQuantity(1);

        List<CartItem> list = new ArrayList<>();
        list.add(cartItemMock1);
        list.add(cartItemMock2);
        cartItemRepository.saveAll(list);
        Assertions.assertNotNull(list);
    }

    @Test
    public void testFindByProduct() {



    }

}
