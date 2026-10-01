package com.pawsstore.shop.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public record JwtProperties(

        long validity,
        String secretKey
) {


}
