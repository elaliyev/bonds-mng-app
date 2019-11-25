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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerResourceTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testCaseShouldReturnAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1))).andDo(print());
    }
    @Test
    public void testCaseShouldReturnCustomerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.age").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Elvin"))
                .andExpect(jsonPath("$.lastName").value("Aliyev"))
                .andExpect(jsonPath("$.age").value(31))
                .andDo(print());
    }

    @Test
    public void testCaseCustomerInvalidArument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/f")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andDo(print());
    }
    @Test
    public void testCaseForInvalidCustomerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/30")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    public void testCaseDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestCaseDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/200")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCaseCreateCustomer() throws Exception {
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
    public void negativeTestCaseCreateCustomer() throws Exception {
        String invalidJsonRequestFoCreateCustomer="{\"first_name\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": \"20\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestFoCreateCustomer)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void testCaseUpdateCustomer() throws Exception {

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
    public void verifyInvalidToDoUpdate() throws Exception {
        String invalidJsonRequestForUpdateCustomer="{\"id\":1,\"first_name\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": \"20\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    public void negativeTestCaseForUpdateCustomerWithDifferentId() throws Exception {
        String invalidJsonRequestForUpdateCustomer="{\"id\":1,\"firstName\": \"Elvin\",\"lastName\": \"Aliyev\",\"age\": 20}";

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonRequestForUpdateCustomer)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
