package com.test.springcloud.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        //http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}","/","/showGetCoupon","/getCoupon")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/showCreateCoupon","/createCoupon","/createResponse")
                .hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/couponapi/coupons","/saveCoupon")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/getCoupon")
                .hasAnyRole("ADMIN","USER"));
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
