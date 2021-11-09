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

import static com.tul.challenge.product.model.ProductTest.getProductByDiscountMock;
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
    @DisplayName("On Created Shopping Cart with product Arena, active discount on product Arena , Total Amount of shopping cart must change")
    public void testUpdateShoppingCart() throws Exception {

        //1 Step: create Empty Shopping Carty and Product Mock
        MvcResult shoppingCartResult = performPostMock("api/shopping/create", "");
        ShoppingCart shoppingCartMock = mapper.readValue(shoppingCartResult.getResponse().getContentAsString(), new TypeReference<ShoppingCart>() {});

        Product productBaseMock = getProductByDiscountMock(false);

        CartItem cartItem = new CartItem();
        Set<CartItem> cartItemSet = new HashSet<>();

        //2 Step: Perform Product create with previous Product Mock
        MvcResult productCreateResult = performPostMock("api/product", Mapping.asJsonString(productBaseMock));
        Product productCreatedMock = mapper.readValue(productCreateResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        //3 Step: Create CartItem
        cartItem.setProduct(productCreatedMock);
        cartItem.setQuantity(2);
        MvcResult cartItemResult = performPostMock("api/cart/", Mapping.asJsonString(cartItem));

        //4 Step: add cart item into Shopping Cart
        //generate expected Shopping Cart TotalAmount
        BigDecimal totalAmountBaseExpected = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())) ;

        //add cart item to shopping cart
        MvcResult shoppingCartWithItemResult = performPostWithPath200StatusMock("api/shopping/{id}", String.valueOf(shoppingCartMock.getId()),Mapping.asJsonString(cartItem) );
        ShoppingCart shoppingCartWithItemMock = mapper.readValue(shoppingCartWithItemResult.getResponse().getContentAsString(), new TypeReference<ShoppingCart>() {});

        //5 Assert total amount expected
        Assertions.assertTrue(totalAmountBaseExpected.compareTo(shoppingCartWithItemMock.getTotalAmount()) == 0);

        //increase price on productBaseMock
        productCreatedMock.setPrice(productCreatedMock.getPrice().add(BigDecimal.valueOf(50.00)));
        MvcResult productUpdatedResult = performPutMock("api/product/{id}", String.valueOf(productCreatedMock.getId()), Mapping.asJsonString(productCreatedMock));

        MvcResult shoppingCartUpdatedResult = performGetByIdMock("api/shopping/{id}", String.valueOf(shoppingCartWithItemMock.getId()));
        ShoppingCart shoppingCartUpdatedMock = mapper.readValue(shoppingCartUpdatedResult.getResponse().getContentAsString(), new TypeReference<ShoppingCart>() {});

        BigDecimal oldTotalAmountShoppingCart = shoppingCartWithItemMock.getTotalAmount();
        BigDecimal newTotalAmountShoppingCartPostUpdateProduct = shoppingCartUpdatedMock.getTotalAmount();
        Assertions.assertTrue(oldTotalAmountShoppingCart.compareTo(newTotalAmountShoppingCartPostUpdateProduct) == -1);

    }

    @Test
    @DisplayName("On Created Cart Item with product Arena, active discount on product Arena , Total Amount of shopping cart must change")
    public void testUpdateProductAndChangeInShoppingCart() throws Exception {

        //1 Step: create Product Mock
        Product productBaseMock = getProductByDiscountMock(false);
        Product productBaseWithDiscountMock = getProductByDiscountMock(true);

        CartItem cartItem = new CartItem();
        Set<CartItem> cartItemSet = new HashSet<>();

        //2 Step: Perform Product create with previous Product Mock
        MvcResult productCreateResult = performPostMock("api/product", Mapping.asJsonString(productBaseMock));
        Product productCreatedMock = mapper.readValue(productCreateResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        //3 Step: Perform Get By ID
        MvcResult productResult = performGetByIdMock("api/product/{id}", String.valueOf(productCreatedMock.getId()));

        //4 Step: set id in the same product but with different discount
        productBaseMock.setId(productCreatedMock.getId());
        productBaseWithDiscountMock.setId(productCreatedMock.getId());

        cartItem.setProduct(productBaseMock);
        cartItem.setQuantity(2);

        BigDecimal oldCartItemAmount = cartItem.getTotalAmountInCartItem();
        //5 Step: create CartItem that includes ProductBaseMock
        performPostMock("api/cart", Mapping.asJsonString(cartItem));

        //6 Step: update product with new product with different discount
        performPutMock("api/product/{id}", String.valueOf(productBaseWithDiscountMock.getId()), Mapping.asJsonString(productBaseWithDiscountMock));

        //7 Step: Get CartItem and see how the price was changed
        MvcResult cartItemResult = performGetByIdMock("api/cart/{id}", String.valueOf(cartItem.getId()));

        CartItem cartItemUpdated = mapper.readValue(cartItemResult.getResponse().getContentAsString(), new TypeReference<CartItem>() {});

        //8 Check: cartItem total amount is Product price x quantity, considering that product was update its discount
        //Check
        BigDecimal productPriceFinal = productBaseMock.getPrice().divide(BigDecimal.valueOf(2));
        BigDecimal totalAmountOnCartItemUpdated = cartItemUpdated.getTotalAmountInCartItem();

        //Must be the same that product price final * 2
        BigDecimal expectedTotalAmountOncART = productPriceFinal.multiply(BigDecimal.valueOf(2));


        Assertions.assertTrue(totalAmountOnCartItemUpdated.compareTo(productPriceFinal.multiply(BigDecimal.valueOf(2))) == 0);
        Assertions.assertNotEquals(cartItemUpdated.getTotalAmountInCartItem(), oldCartItemAmount);

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
    public MvcResult performPostWithPathMock(String url, String pathId, String content) throws Exception {
        return mvc.perform(post(baseUrl.concat(url), pathId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated()).andReturn();
    }

    public MvcResult performPostWithPath200StatusMock(String url, String pathId, String content) throws Exception {
        return mvc.perform(post(baseUrl.concat(url), pathId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk()).andReturn();
    }

    public MvcResult performPutMock(String url, String pathId, String content) throws Exception {
         return mvc.perform(put(baseUrl.concat(url), pathId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk()).andReturn();
    }

}
