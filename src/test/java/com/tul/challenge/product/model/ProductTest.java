package com.tul.challenge.product.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootApplication
public class ProductTest {

    @Test
    @DisplayName("UpdateDiscount, when product is only update its discountField, it must change the price by its discount")
    public void updateDiscountTest() {

        Product product = getProductMock();
        BigDecimal oldPrice = product.getPrice();

        Product productRequest = getProductByDiscountMock(!product.isDiscount());
        product.updateProduct(productRequest);

        assertThat(oldPrice).isNotEqualByComparingTo(product.getPrice());
    }


    @Test
    @DisplayName("UpdateDiscount, when product is updated its Price and discounts fields, it must not change the price by its discount")
    public void updatePriceAndDiscountTest() {

        Product product = getProductMock();

        BigDecimal oldPrice = product.getPrice();
        BigDecimal newPrice = product.getPrice().add(BigDecimal.valueOf(13.00));

        Product productRequest = getProductByPriceAndDiscountMock(newPrice, !product.isDiscount());

        product.updateProduct(productRequest);

        assertThat(product.getPrice()).isEqualByComparingTo(newPrice);
    }

    public static Product getProductMock(){
        return Product.builder()
                .id(UUID.randomUUID())
                .name("ProductMock")
                .price(BigDecimal.valueOf(12.50))
                .description("This is a ProductMock")
                .SKU("MOCK-1")
                .discount(false)
                .build();
    }

    public static Product getProductWithDiscountMock(){
        return Product.builder()
                .id(UUID.randomUUID())
                .name("ProductMock")
                .price(BigDecimal.valueOf(12.50))
                .description("This is a ProductMock")
                .SKU("MOCK-1")
                .discount(true)
                .build();
    }

    public static Product getProductByDiscountMock(boolean discount){
        Product productByDiscountMock = getProductMock();
        productByDiscountMock.setDiscount(discount);

        return productByDiscountMock;
    }

    public Product getProductByPriceAndDiscountMock(BigDecimal price, boolean discount){
        Product productByPriceAndDiscountMock = getProductMock();
        productByPriceAndDiscountMock.setDiscount(discount);
        productByPriceAndDiscountMock.setPrice(price);

        return productByPriceAndDiscountMock;
    }

    public static Product getProductByPriceMock(BigDecimal price){
        return Product.builder()
                .id(UUID.randomUUID())
                .name("ProductMock")
                .price(price)
                .description("This is a ProductMock")
                .SKU("MOCK-1")
                .discount(false)
                .build();
    }

}
