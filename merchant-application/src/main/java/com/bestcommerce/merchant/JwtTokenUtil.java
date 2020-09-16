package com.bestcommerce.merchant;

import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60L;

    private static final String SECRET= "TEST-SECRET";

    public String generateToken(MerchantDTO details) {
        Map<String, Object> claims = Map.of(
                "merchant-name", details.getName(),
                "merchant-type", details.getType(),
                "owner-name", details.getOwnerDTO().getName(),
                "owner-email", details.getOwnerDTO().getEmail(),
                "owner-phone", details.getOwnerDTO().getPhone(),
                "owner-address", details.getOwnerDTO().getAddress());

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

}