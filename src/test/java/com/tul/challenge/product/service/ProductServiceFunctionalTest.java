package com.tul.challenge.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tul.challenge.product.model.Product;
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

import static com.tul.challenge.product.model.ProductTest.getProductMock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceFunctionalTest {


        @Autowired
        private MockMvc mvc;

        private Product productMock;

        private ObjectMapper mapper;

        private final String baseUrl = "http://localhost:8080/";

    @BeforeEach()
    public void setUp(){
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        productMock = getProductMock();
    }

    @Test()
    @DisplayName("Test only update discount on product, must change its price")
    public void testUpdateDiscountChangePrice() throws Exception {
        BigDecimal oldPrice = productMock.getPrice();
        MvcResult productCreatedResult = performPostMock("api/product", Mapping.asJsonString(productMock));
        Product productCreatedMock = mapper.readValue(productCreatedResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        productCreatedMock.setDiscount(!productCreatedMock.isDiscount());
        MvcResult productUpdatedResult= performPutMock("api/product/{id}", String.valueOf(productCreatedMock.getId()),Mapping.asJsonString(productCreatedMock));
        Product productUpdatedMock = mapper.readValue(productUpdatedResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        MvcResult productGetResult =  performGetByIdMock("api/product/{id}", String.valueOf(productCreatedMock.getId()));
        Product productGetMock = mapper.readValue(productGetResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        Assertions.assertNotEquals(oldPrice, productGetMock.getPrice());
        Assertions.assertTrue(oldPrice.divide(BigDecimal.valueOf(2)).compareTo(productGetMock.getPrice()) == 0 );
    }


    @Test()
    @DisplayName("Test update discount and price on product, must not change its price by discount")
    public void testUpdateDiscountAndOtherFieldsMustNotChangeItsPrice() throws Exception {
        BigDecimal newPrice = BigDecimal.valueOf(570.00);
        MvcResult productCreatedResult = performPostMock("api/product", Mapping.asJsonString(productMock));
        Product productCreatedMock = mapper.readValue(productCreatedResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        productCreatedMock.setDiscount(!productCreatedMock.isDiscount());
        productCreatedMock.setPrice(newPrice);
        MvcResult productUpdatedResult= performPutMock("api/product/{id}", String.valueOf(productCreatedMock.getId()),Mapping.asJsonString(productCreatedMock));
        Product productUpdatedMock = mapper.readValue(productUpdatedResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        MvcResult productGetResult =  performGetByIdMock("api/product/{id}", String.valueOf(productCreatedMock.getId()));
        Product productGetMock = mapper.readValue(productGetResult.getResponse().getContentAsString(), new TypeReference<Product>() {});

        Assertions.assertTrue(newPrice.compareTo(productGetMock.getPrice()) == 0);
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

    public MvcResult performPutMock(String url, String pathId, String content) throws Exception {
        return mvc.perform(put(baseUrl.concat(url), pathId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk()).andReturn();
    }

    }