package com.ecommerce.ecommerce.auth.token.service;

import com.ecommerce.ecommerce.auth.token.helper.TokenHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    public String extractEmail ( String token ) {
        return TokenHelper.extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid ( String token, UserDetails userDetails ) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired ( String token ) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration ( String token ) {
        return TokenHelper.extractClaim(token, Claims::getExpiration);
    }
}
