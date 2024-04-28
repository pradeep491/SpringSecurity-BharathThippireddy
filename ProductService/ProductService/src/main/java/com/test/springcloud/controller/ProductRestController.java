package com.test.springcloud.controller;

import com.test.springcloud.dto.Coupon;
import com.test.springcloud.model.Product;
import com.test.springcloud.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productapi")
@Slf4j
public class ProductRestController {
    private final ProductRepo repo;
    private final RestTemplate template;

    private final WebClient webClient;

    @Value("${couponService.url}")
    private String couponServiceURL;

    public ProductRestController(ProductRepo repo, RestTemplate template,WebClient webClient) {
        this.repo = repo;
        this.template = template;
        this.webClient = webClient;
    }

    @PostMapping("/products")
    public Product create(@RequestBody Product product) {
        log.info("using RestTemplate");
        Coupon coupon = template.getForObject(couponServiceURL + product.getCouponCode(), Coupon.class);


        /*log.info("using webclient");
        Coupon coupon = webClient.get()
                .uri(couponServiceURL +  product.getCouponCode())
                .retrieve()
                .bodyToMono(Coupon.class)
                .block();*/

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
    @GetMapping("/product/getProducts")
    public List<Product> getProducts(){
        return repo.findAll();
    }
}
