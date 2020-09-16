package com.bestcommerce.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MerchantApplication {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static void main(String[] args) {
        SpringApplication.run(MerchantApplication.class, args);
    }

}
