package com.bonds.rest;

import com.bonds.exception.BadRequestException;
import com.bonds.exception.NotFoundException;
import com.bonds.model.Coupon;
import com.bonds.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CouponResource {

    @Autowired
    private CouponService couponService;


    @PostMapping("coupons/save")
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Resource<Coupon>> createCoupon(@RequestBody Coupon coupon) {

        if(coupon.getRate()==0) coupon.setRate(5.0);
        if(coupon.getTerm()==0) coupon.setTerm(5);
        Coupon savedCoupon = couponService.save(coupon);

        Resource<Coupon> couponResource = new Resource<Coupon>(savedCoupon);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getCouponById(savedCoupon.getId()));
        couponResource.add(linkTo.withRel("coupons/{id}"));

        return new ResponseEntity<Resource<Coupon>>(couponResource, HttpStatus.CREATED);

    }

    @GetMapping("coupons/{id}")
    @ExceptionHandler({BadRequestException.class })
    public ResponseEntity<Coupon> getCouponById(@PathVariable long id) {

        Optional<Coupon> coupon = couponService.findById(id);
        if (!coupon.isPresent())
            throw new NotFoundException("id",id,"Coupon");

        return new ResponseEntity<Coupon>(coupon.get(),HttpStatus.OK);
    }

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons() {
        return couponService.findAll();
    }

    @PutMapping("/coupons/{id}")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody Coupon coupon, @PathVariable long id) {

        Optional<Coupon> couponOptional = couponService.findById(id);

        if (!couponOptional.isPresent())
            return ResponseEntity.notFound().build();

        if(id!=coupon.getId()){
            throw new BadRequestException("The id is not match");
        }
        coupon.setId(id);
        Coupon updatedCoupon = couponService.save(coupon);

        return new ResponseEntity<Coupon>(updatedCoupon, HttpStatus.CREATED);
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity deleteCoupon(@PathVariable long id)
    {
        Optional<Coupon> coupon = couponService.findById(id);

        if (!coupon.isPresent())
            throw new NotFoundException("id",id,"Coupon");

        couponService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
