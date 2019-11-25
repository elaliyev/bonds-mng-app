package com.bonds.repository;

import com.bonds.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    @Override
    Optional<Coupon> findById(Long id);

    @Override
    List<Coupon> findAll();

    @Override
    Coupon  save(Coupon coupon);

    @Override
    void deleteById(Long id);
}
