package com.bestcommerce.merchant;

import com.bestcommerce.merchant.jooq.dto.MerchantDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("${app.jwt.validity-period-ms}")
    private Long period;

    @Value("${app.jwt.secret}")
    private String secret;

    public String generateToken(MerchantDTO details) {
        Map<String, Object> claims = Map.of(
                "merchant-name", details.getName(),
                "merchant-type", details.getType(),
                "owner-name", details.getOwnerDTO().getName(),
                "owner-email", details.getOwnerDTO().getEmail(),
                "owner-phone", details.getOwnerDTO().getPhone(),
                "owner-address", details.getOwnerDTO().getAddress());

        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + period))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}