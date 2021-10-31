package challenge.shopping.cart.repository;


import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.repository.CartItemRepository;
import com.tul.challenge.product.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class TestCartItemRepository {

    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private TestEntityManager testEntityManager;

    @Test
    public void testSaveItem() {
        Integer productId = 1;

        Product product = testEntityManager.find(Product.class, productId);

        CartItem cartItemMock = new CartItem();
        cartItemMock.setProduct(product);
        cartItemMock.setQuantity(1);

        CartItem cartItem = cartItemRepository.save(cartItemMock);
        System.out.println(cartItem);
    }

}
