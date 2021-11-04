package com.tul.challenge.product.service;

import com.tul.challenge.product.exceptions.ProductNotFoundException;
import com.tul.challenge.product.model.Product;
import com.tul.challenge.product.repository.ProductRepository;
import com.tul.challenge.product.services.ProductService;
import com.tul.challenge.product.services.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    private final UUID id = UUID.fromString("ab96f5f6-8c91-4336-9699-d8d59be54600");

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        productService =  new ProductServiceImpl( productRepository);
        Product mockProduct =  Product.builder()
                                    .id(UUID.randomUUID())
                                    .name("Arena")
                                    .price(BigDecimal.valueOf(12.50))
                                    .description("Arena de mar")
                                    .SKU("AR-4")
                                    .discount(false)
                                    .build();

        Mockito.when(productRepository.findById(id))
                .thenReturn(Optional.of(mockProduct));
        Mockito.when(productRepository.save(mockProduct)).thenReturn(mockProduct);

    }

    @Test
    @DisplayName("GetProductTest, get correctly a product when call getProduct")
    public void whenValidGetID_ThenReturnProduct(){
        Product found = productService.getProduct(id);
        assertThat(found.getName()).isEqualTo("Arena");
    }

    @Test
    @DisplayName("GetProduct, Throw Product not found exception")
    public void whenNotFoundID_ThenReturnProductNotFoundException() throws ProductNotFoundException {

        Throwable exception = assertThrows(ProductNotFoundException.class, () ->productService.getProduct(UUID.randomUUID()));
        String expectedMessage = "Product not found";

        assertEquals(expectedMessage, exception.getMessage());
    }

}