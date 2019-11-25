package com.bonds.bondsmanagement;

import com.bonds.BondsManagementApplication;
import com.bonds.model.Application;
import com.bonds.model.Coupon;
import com.bonds.model.Customer;
import com.bonds.repository.ApplicationRepository;
import com.bonds.repository.CustomerRepository;
import com.bonds.service.ApplicationServiceImpl;
import com.bonds.service.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCaseGetAllApplications(){
        List<Application> applications = Arrays.asList(
            new Application(1,5,new Customer(), new Coupon(), new BigDecimal("100")),
            new Application(1,5,new Customer(), new Coupon(), new BigDecimal("100")),
            new Application(1,5,new Customer(), new Coupon(), new BigDecimal("100"))
        );
        when(applicationRepository.findAll()).thenReturn(applications);

        List<Application> result = applicationService.findAll();
        assertEquals(3, result.size());
    }
    @Test
    public void testCaseGetApplicationById(){
        Application application = new Application(1,5,new Customer(1l,null,null,-1), new Coupon(2l,0.0,5,null), new BigDecimal("100"));
        when(applicationRepository.findById(1l).get()).thenReturn(application);

        Application result = applicationService.findById(1).get();
        assertEquals(5, result.getTerm());
        assertEquals((Long)1l, result.getCustomer().getId());
        assertEquals((Long)2l, result.getCoupon().getId());
        assertEquals(100,new BigDecimal("100"));
    }
    @Test
    public void testCaseCreateApplication(){
        Application application = new Application(1,5,new Customer(1l,null,null,-1), new Coupon(2l,0.0,5,null), new BigDecimal("100"));
        when(applicationRepository.save(application)).thenReturn(application);
        Application result = applicationService.save(application);
        assertEquals(5, result.getTerm());
        assertEquals((Long)1l, result.getCustomer().getId());
        assertEquals((Long)2l, result.getCoupon().getId());
        assertEquals(100,new BigDecimal("100"));
    }
    @Test
    public void testCaseRemoveApplication(){
        Application application = new Application(1,5,new Customer(1l,null,null,-1), new Coupon(2l,0.0,5,null), new BigDecimal("100"));
        applicationService.deleteById(application.getId());
        verify(applicationRepository, times(1)).deleteById(application.getId());
    }
}
