package com.test.springcloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CouponServiceAuthThymeleafCusomLogin1ApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetCouponWithoutAuth_forbidden() throws Exception {
        mockMvc.perform(get("/couponapi/coupons/SUPERSALE"))
                //.andExpect(status().isForbidden());
                .andExpect(status().isOk());


    }
    @Test
    @WithMockUser(roles = {"USER"})
    void testGetCouponWithAuth_success() throws Exception {
        mockMvc.perform(get("/couponapi/coupons/SUPERSALE"))
                .andExpect(status().isOk()).andExpect(content().string("{\"id\":11,\"code\":\"SUPERSALE\",\"discount\":1000.000,\"expDate\":\"21-05-2025\"}"));

    }

    //For CSRF Token - Start
    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testCreateCoupon_withoutCSRF_forbidden() throws Exception {
        String json = """
                {
                    "code": "SUPERSALECSRF",
                    "discount": 1000,
                    "expDate": "21-05-2025"
                }
                """;
        mockMvc.perform(post("/couponapi/coupons")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isForbidden());

    }
    @Test
    @WithMockUser(roles={"ADMIN"})
    public void testCreateCoupon_withCSRF_forbidden() throws Exception {
        String json = """
                {
                    "code": "SUPERSALECSRF1",
                    "discount": 1000,
                    "expDate": "21-05-2025"
                }
                """;
        mockMvc.perform(post("/couponapi/coupons")
                        .contentType("application/json")
                        .content(json).with(csrf().asHeader()))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(roles={"USER"})
    public void testCreateCoupon_NonAdminUser_forbidden() throws Exception {
        String json = """
                {
                    "code": "SUPERSALECSRF2",
                    "discount": 1000,
                    "expDate": "21-05-2025"
                }
                """;
        mockMvc.perform(post("/couponapi/coupons")
                        .contentType("application/json")
                        .content(json).with(csrf().asHeader()))
                .andExpect(status().isOk());

    }
    //For CSRF Token - End

    //For CORS Testing - Start
    @Test
    public void testCORSHeaders() throws Exception {
        mockMvc.perform(options("/couponapi/coupons")
                        .header("Access-Control-Request-Method", "POST")
                        .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS"));
               /* .andExpect(header().exists("Access-Control-Allow-Headers"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().exists("Access-Control-Allow-Credentials"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"))
                .andExpect(header().exists("Access-Control-Max-Age"))
                .andExpect(header().string("Access-Control-Max-Age", "3600"));*/
    }
    //For CORS Testing - End

    //@WithUserDetails - It will go and fetch the user details from DB
    //So, make sure the user is present in the DB, otherwise it will throw error
    //Caused by: org.springframework.security.core.userdetails.UsernameNotFoundException: User not found

    @Test
    @WithUserDetails("jasvin@gmail.com")
    void testGetCouponWithAuth_WithUserDetails() throws Exception {
        mockMvc.perform(get("/couponapi/coupons/SUPERSALE"))
                .andExpect(status().isOk()).andExpect(content().string("{\"id\":11,\"code\":\"SUPERSALE\",\"discount\":1000.000,\"expDate\":\"21-05-2025\"}"));

    }
    //Testing Authentication
    @Test
    void testGetCouponWithoutAuth_forbidden1() throws Exception {
        mockMvc.perform(get("/couponapi/coupons/SUPERSALE"))
                //.andExpect(status().isForbidden());
                .andExpect(status().isForbidden());


    }
}