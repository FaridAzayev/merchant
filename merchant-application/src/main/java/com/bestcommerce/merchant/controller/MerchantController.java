package com.bestcommerce.merchant.controller;

import com.bestcommerce.merchant.kafka.consumer.SignUpConsumer;
import org.springframework.stereotype.Controller;

@Controller
public class MerchantController {

    private SignUpConsumer signUpConsumer;

    public MerchantController(SignUpConsumer signUpConsumer) {
        this.signUpConsumer = signUpConsumer;
    }
}
