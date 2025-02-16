package com.fuli.tradingsystem.order.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fuli.tradingsystem.TestConfiguration;


@SpringBootTest(classes = {TestConfiguration.class})
@AutoConfigureMockMvc
public class PlaceOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    
    @BeforeEach
    public void setUp() {

	mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testPlaceStockOrder() throws Exception {

	// TODO mock the `backend` service
        mockMvc.perform(post("/api/stock/order/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"price\": \"9.89\", \"symbol\":\"KS200400F5.KS\", \"quantity\": \"1000\", \"side\": \"Buy\"}"))
                .andExpect(status().isOk()).andExpect(content().json("""
        	{
                    "message": "Failed to place order, validation failed",
                    "status": "Blocked",
                    "validationResults": [
                        {
                            "level": "Pass",
                            "message": "Basic info validation passed."
                        },
                        {
                            "level": "Block",
                            "message": "VariationType:TickSize,AllowedVariation:8.00,Scenario:Disadvantage,Price:9.89,ReferencePrice:8.88,ActualVariation:101.00,Buy higher,Block"
                        }
                    ]
                }"""));
    }

}