package com.test.springcloud.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Coupon {
    private Long id;
    private String code;
    private BigDecimal discount;
    private String expDate;
}