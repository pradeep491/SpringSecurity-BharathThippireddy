package com.test.springcloud.controller;

import com.test.springcloud.model.Coupon;
import com.test.springcloud.repo.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

    @Autowired
    CouponRepo repo;

    @PostMapping("/coupons")
    public Coupon create(@RequestBody Coupon coupon) {
        return repo.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    public Coupon getCoupons(@PathVariable("code") String code) {
        return repo.findByCode(code);
    }
}
