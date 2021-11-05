package com.tul.challenge.shopping.cart.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tul.challenge.product.model.Product;
import com.tul.challenge.shopping.cart.model.CartItem;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.utils.Mapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.tul.challenge.product.model.ProductTest.getProductMock;
import static com.tul.challenge.shopping.cart.model.ShoppingCartTest.getShoppingCartMock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartFunctionalTest {

    private ShoppingCart shoppingCartMock;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper;

    private final String baseUrl = "http://localhost:8080/";

    @BeforeEach()
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        shoppingCartMock = getShoppingCartMock();
    }

    @Test
    @DisplayName("On Update Cart Item, Total Amount of shopping cart must change")
    public void testUpdateProductAndChangeInShoppingCart() throws Exception {

        //1 Step: create Product Mock
        Product productBaseMock = getProductMock();

        CartItem cartItem = new CartItem();
        Set<CartItem> cartItemSet = new HashSet<>();

        //2 Step: Perform Product create with previous Product Mock
        MvcResult productCreateResult = performPostMock("api/product", Mapping.asJsonString(productBaseMock));
        Product productCreatedMock = mapper.readValue(productCreateResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        //3 Step: Perform Get By ID
        MvcResult productResult = performGetByIdMock("api/product/{id}", String.valueOf(productCreatedMock.getId()));

        //4 Step: create productUpdatedMock through productBaseMock wit different discount (This will change the price)
        Product productUpdatedMock = productBaseMock;
        productUpdatedMock.setDiscount(!productBaseMock.isDiscount());

        cartItem.setProduct(productUpdatedMock);
        cartItem.setQuantity(2);

        //5 Step: create CartItem that includes ProductBaseMock
        performPostMock("api/cart", Mapping.asJsonString(cartItem));

        //6 Step: update product with new product with different discount
        performPutMock("api/product/{id}", String.valueOf(productBaseMock.getId()), Mapping.asJsonString(productUpdatedMock));

        //7 Step: Get CartItem and see how the price was changed
        MvcResult cartItemResult = performGetByIdMock("api/cart/{id}", String.valueOf(cartItem.getId()));

        CartItem cartItemUpdated = mapper.readValue(cartItemResult.getResponse().getContentAsString(), new TypeReference<CartItem>() {});

        //8 Check: cartItem total amount is Product price x quantity, considering that product was update its discount

        //Check
        BigDecimal productPriceFinal = productBaseMock.getPrice().divide(BigDecimal.valueOf(2));
        Assertions.assertEquals(cartItemUpdated.getTotalAmountInCartItem(), productPriceFinal.multiply(BigDecimal.valueOf(2)));


    }





    public MvcResult performGetAllMock(String url) throws Exception {
       return mvc.perform(get(baseUrl.concat(url)))
                                     .andExpect(status().isOk()).andReturn();
    }

    public MvcResult performGetByIdMock(String url, String pathId) throws Exception {
        return mvc.perform(get(baseUrl.concat(url), pathId))
                .andExpect(status().isOk()).andReturn();
    }



    public MvcResult performPostMock(String url, String content) throws Exception {
        return mvc.perform(post(baseUrl.concat(url))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(content))
                                    .andExpect(status().isCreated()).andReturn();
    }

    public void performPutMock(String url, String pathId, String content) throws Exception {
        MvcResult result = mvc.perform(put(baseUrl.concat(url), pathId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated()).andReturn();
    }


}
