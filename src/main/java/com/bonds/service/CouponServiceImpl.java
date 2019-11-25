package com.bonds.service;

import com.bonds.model.Coupon;
import com.bonds.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository repository;

    @Override
    public List<Coupon> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Coupon> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return repository.save(coupon);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }


}
