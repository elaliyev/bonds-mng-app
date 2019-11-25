package com.bonds.bondsmanagement;

import com.bonds.BondsManagementApplication;
import com.bonds.model.Application;
import com.bonds.model.Coupon;
import com.bonds.model.Customer;
import com.bonds.repository.ApplicationRepository;
import com.bonds.service.ApplicationService;
import com.bonds.service.ApplicationServiceImpl;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationResourceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private ApplicationServiceImpl applicationRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testCaseShouldReturnAllApplications() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/applications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))).andDo(print());

    }
    @Test
    public void testCaseShouldReturnApplicationById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/1001")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.term").exists())
                .andExpect(jsonPath("$.paidAmount").exists())
                .andExpect(jsonPath("$.applicationStatus").exists())
                .andExpect(jsonPath("$.coupon").exists())
                .andExpect(jsonPath("$.customer").exists())
                .andExpect(jsonPath("$.ipAddress").exists())
                .andExpect(jsonPath("$.couponTotalAmount").exists())
                .andExpect(jsonPath("$.applicationDate").exists())
                .andExpect(jsonPath("$.id").value(1001))
                .andExpect(jsonPath("$.paidAmount").value(new BigDecimal("220.0")))
                .andExpect(jsonPath("$.couponTotalAmount").value(new BigDecimal("220.0")))
                .andDo(print());

    }

    @Test
    public void testCaseApplicationInvalidArument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/f")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andDo(print());
    }
    @Test
    public void testCaseForInvalidApplicationId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    public void testCaseDeleteApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestCaseDeleteApplication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/applications/200")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCaseCreateApplication() throws Exception {
        String jsonRequestFoCreateCustomer="{\"firstName\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": 20}";

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestFoCreateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.firstName").value("Elvin"))
                .andExpect(jsonPath("$.lastName").value("Aliyev"))
                .andExpect(jsonPath("$.age").value(20))
                .andDo(print());
    }
    @Test
    public void negativeTestCaseCreateApplication() throws Exception {
        String invalidJsonRequestFoCreateCustomer="{\"first_name\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": \"20\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestFoCreateCustomer)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void testCaseUpdateApplication() throws Exception {

        String jsonRequestFoCreateCustomer="{\"id\":1,\"firstName\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": 20}";

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestFoCreateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.firstName").value("Elvin"))
                .andExpect(jsonPath("$.lastName").value("Aliyev"))
                .andExpect(jsonPath("$.age").value(20))
                .andDo(print());
    }

    @Test
    public void verifyInvalidApplicationUpdate() throws Exception {
        String invalidJsonRequestForUpdateCustomer="{\"id\":1,\"first_name\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": \"20\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void negativeTestCaseForUpdateApplicationWithDifferentId() throws Exception {
        String invalidJsonRequestForUpdateCustomer="{\"id\":1,\"firstName\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": 20}";

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
