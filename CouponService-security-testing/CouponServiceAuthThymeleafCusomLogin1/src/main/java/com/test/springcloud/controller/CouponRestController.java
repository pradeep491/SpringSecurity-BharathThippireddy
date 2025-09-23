package com.test.springcloud.controller;

import com.test.springcloud.model.Coupon;
import com.test.springcloud.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

    @Autowired
    CouponRepo repo;

    @PostMapping("/coupons")
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon create(@RequestBody Coupon coupon) {
        return repo.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Coupon getCoupons(@PathVariable("code") String code) {
        return repo.findByCode(code);
    }
}
