package com.test.springcloud.controller;

import com.test.springcloud.model.Coupon;
import com.test.springcloud.repo.CouponRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {

    private final CouponRepo repo;

    public CouponRestController(CouponRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/coupons")
    public Coupon create(@RequestBody Coupon coupon) {
        return repo.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    public Coupon getCoupons(@PathVariable("code") String code) {
        return repo.findByCode(code);
    }
    @GetMapping("/coupons/getCoupons")
    public List<Coupon> getAllCoupons() {
        return repo.findAll();
    }
}
