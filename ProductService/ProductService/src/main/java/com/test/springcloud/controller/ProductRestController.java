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
    private RestTemplate template;

    @Value("${couponService.url}")
    private String couponServiceURL;

    @PostMapping("/products")
    public Product create(@RequestBody Product product) {
        Coupon coupon = template.getForObject(couponServiceURL + product.getCouponCode(), Coupon.class);
        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return repo.save(product);
    }
    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId){
        Optional<Product> product = repo.findById(productId);
        if(product.isPresent()){
            return product.get();
        }else{
            throw new RuntimeException("Product not found with id-"+productId);
        }
    }
}
