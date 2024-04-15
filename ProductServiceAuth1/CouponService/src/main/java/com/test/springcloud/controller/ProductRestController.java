package com.test.springcloud.controller;

import com.test.springcloud.dto.Coupon;
import com.test.springcloud.model.Product;
import com.test.springcloud.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {

    @Autowired
    ProductRepo repo;

    @Autowired
    RestTemplate template;

    @Value("${couponService.url}")
    private String url;

    @PostMapping("/products")
    public Product create(@RequestBody Product product) {
        Coupon coupon = template.getForObject(url + product.getCouponCode(), Coupon.class);
        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return repo.save(product);
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId) {
        System.out.println("control is in Get()");
        Optional<Product> product = repo.findById(productId);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found for the product code-" + productId);
        }
    }
}
