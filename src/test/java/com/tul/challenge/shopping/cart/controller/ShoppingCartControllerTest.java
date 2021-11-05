package com.tul.challenge.shopping.cart.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tul.challenge.shopping.cart.model.ShoppingCart;
import com.tul.challenge.shopping.cart.repository.ShoppingCartRepository;
import com.tul.challenge.shopping.cart.services.ShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static com.tul.challenge.shopping.cart.model.ShoppingCartTest.getShoppingCartMock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    private ShoppingCart shoppingCart;

    private ObjectMapper mapper;

    private final String baseUrl = "http://localhost:8080/api/shopping/";

    private final UUID id = UUID.fromString("ab96f5f6-8c91-4336-9699-d8d59be54617");


    @BeforeEach
    public void setUp() {
        shoppingCart = getShoppingCartMock();
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Mockito.when(shoppingCartService.getShoppingCart(id)).thenReturn(shoppingCart);
        Mockito.when(shoppingCartService.listAllShoppingCart()).thenReturn(List.of(shoppingCart));
    }


    @Test
    public void testing() throws Exception {
        MvcResult mvcResult = mvc.perform(get(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

                MvcResult result = mvc.perform(get(baseUrl))
                        .andExpect(status().isOk())
                        .andReturn();

        List<ShoppingCart> actual = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ShoppingCart>>() {});
        Assertions.assertEquals(actual, List.of(shoppingCart));
    }
}
