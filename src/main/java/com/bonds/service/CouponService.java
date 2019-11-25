package com.bonds.service;

import com.bonds.model.Coupon;
import java.util.List;
import java.util.Optional;

public interface CouponService {

    List<Coupon> findAll();
    Optional<Coupon> findById(long id);
    Coupon save(Coupon coupon);
    void deleteById(long id);
}
