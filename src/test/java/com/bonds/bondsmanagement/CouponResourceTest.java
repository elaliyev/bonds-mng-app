package com.bonds.bondsmanagement;


import com.bonds.BondsManagementApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CouponResourceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testAllCoupons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))).andDo(print());
    }
    @Test
    public void testCouponById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/101").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rate").exists())
                .andExpect(jsonPath("$.term").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.rate").value(5))
                .andExpect(jsonPath("$.term").value(5))
                .andExpect(jsonPath("$.price").value(100))
                .andDo(print());
    }

    @Test
    public void verifyInvalidToDoArgument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/f").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andDo(print());
    }
    @Test
    public void verifyInvalidCouponId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/f").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    public void verifyDeleteCoupon() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/101").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void verifyInvalidToDoIdToDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/200").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void verifySaveToDo() throws Exception {
        String jsonRequestFoCreateCoupon="{\"coupon\":{\"rate\": 8,\"term\": 15,\"price\": 200}";

        mockMvc.perform(MockMvcRequestBuilders.post("/coupons/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestFoCreateCoupon)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rate").exists())
                .andExpect(jsonPath("$.term").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.rate").value(8))
                .andExpect(jsonPath("$.term").value(15))
                .andExpect(jsonPath("$.price").value(new BigDecimal("200")))
                .andDo(print());
    }
    @Test
    public void verifyMalformedSaveToDo() throws Exception {
        String invalidJsonRequestFoCreateCoupon="{\"rate\": \"8\",\"term\": 15,\"price\": 200}";

        mockMvc.perform(MockMvcRequestBuilders.post("/coupons/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestFoCreateCoupon)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void verifyUpdateToDo() throws Exception {

        String jsonRequestFoUpdateCoupon="{\"id\":101,\"rate\": 8,\"term\": 15,\"price\": 200}";

        mockMvc.perform(MockMvcRequestBuilders.put("/coupons/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestFoUpdateCoupon)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.rate").exists())
                .andExpect(jsonPath("$.term").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.rate").value(8))
                .andExpect(jsonPath("$.term").value(15))
                .andExpect(jsonPath("$.price").value(new BigDecimal("200")))
                .andDo(print());
    }

    @Test
    public void verifyInvalidToDoUpdate() throws Exception {
        String invalidJsonRequestFoCreateCoupon="{\"id\":1,\"rate\": \"8\",\"term\": 15,\"price\": 200}";

        mockMvc.perform(MockMvcRequestBuilders.put("/coupons/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestFoCreateCoupon)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void verifyInvalidToDoUpdateIfIdDifferent() throws Exception {
        String invalidJsonRequestFoCreateCoupon="{\"id\":1,\"rate\": 8,\"term\": 15,\"price\": 200}";

        mockMvc.perform(MockMvcRequestBuilders.put("/coupons/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestFoCreateCoupon)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
