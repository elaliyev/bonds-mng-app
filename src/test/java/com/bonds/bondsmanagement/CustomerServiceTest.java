package com.bonds.bondsmanagement;

import com.bonds.BondsManagementApplication;
import com.bonds.model.Customer;
import com.bonds.repository.CustomerRepository;
import com.bonds.service.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCaseGetAllCustomers(){
        List<Customer> customers = Arrays.asList(
                new Customer(1l,"A1","B1",20),
                new Customer(2l,"A2","B2",30),
                new Customer(3l,"A3","B3",40)
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.findAll();
        assertEquals(3, result.size());
    }
    @Test
    public void testCaseGetCustomerById(){
        Customer customer = new Customer(1l,"A1","B1",40);
        when(customerRepository.findById(1l).get()).thenReturn(customer);
        Customer result = customerService.findById(1).get();
        assertEquals("A1", result.getFirstName());
        assertEquals("A2", result.getLastName());
        assertEquals(40,result.getAge());
    }
    @Test
    public void testCaseSaveCustomer(){
        Customer customer = new Customer(1l,"A1","B1",40);
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer result = customerService.save(customer);
        assertEquals("A1", result.getFirstName());
        assertEquals("A2", result.getLastName());
        assertEquals(40,result.getAge());
    }
    @Test
    public void removeCoupon(){
        Customer customer = new Customer(1l,"Elvin","Aliyev",31);
        customerService.deleteById(customer.getId());
        verify(customerRepository, times(1)).deleteById(customer.getId());
    }
}
