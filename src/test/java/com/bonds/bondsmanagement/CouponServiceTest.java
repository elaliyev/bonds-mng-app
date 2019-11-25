package com.bonds.bondsmanagement;

import com.bonds.BondsManagementApplication;
import com.bonds.model.Coupon;
import com.bonds.repository.CouponRepository;
import com.bonds.service.CouponServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BondsManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testGetAllCoupons(){
        List<Coupon> coupons = new ArrayList<Coupon>();
        coupons.add(new Coupon(1l,3,10,new BigDecimal("50")));
        coupons.add(new Coupon(2l,4,15,new BigDecimal("50")));
        coupons.add(new Coupon(3l,5,20,new BigDecimal("50")));

        when(couponRepository.findAll()).thenReturn(coupons);

        List<Coupon> result = couponService.findAll();
        assertEquals(3, result.size());
    }
    @Test
    public void testGetCouponById(){
        Coupon coupon = new Coupon(1l,3,10,new BigDecimal("50"));
        when(couponRepository.findById(1l).get()).thenReturn(coupon);
        Coupon result = couponService.findById(1).get();
        assertEquals(3, result.getRate());
        assertEquals(10, result.getTerm());
        assertEquals(new BigDecimal("50"), result.getPrice());
    }
    @Test
    public void saveCoupon(){
        Coupon coupon = new Coupon(1l,3,10,new BigDecimal("50"));
        when(couponRepository.save(coupon)).thenReturn(coupon);
        Coupon result = couponService.save(coupon);
        assertEquals(3d, result.getRate());
        assertEquals(10, result.getTerm());
        assertEquals(new BigDecimal("50"), result.getPrice());
    }
    @Test
    public void removeCoupon(){
        Coupon coupon = new Coupon(1l,3,10,new BigDecimal("50"));
        couponService.deleteById(coupon.getId());
        verify(couponRepository, times(1)).deleteById(coupon.getId());
    }
}
