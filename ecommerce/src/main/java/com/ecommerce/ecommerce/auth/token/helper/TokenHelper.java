package com.ecommerce.ecommerce.auth.token.helper;

import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.function.Function;

public class TokenHelper {

    @Value("${security.secret-key}")
    private static String secretKey;

    @Value("${security.token-validity}")
    private static long jwtExpiration;

    public static long getJwtExpiration() {
        return jwtExpiration;
    }

    public static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(TokenHelper.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}