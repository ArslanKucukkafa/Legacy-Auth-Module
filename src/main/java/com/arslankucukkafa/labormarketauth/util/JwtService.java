package com.arslankucukkafa.labormarketauth.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtService implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);


    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.token.validity}") long tokenValidity) {
    }

    public String generateToken(String subject, String secret ,long validity){
        try {
            return Jwts.builder().subject(subject).expiration(
                            new Date(System.currentTimeMillis()+1000*validity))
                    .issuer("604ef5b2eea6b60a8dc809bfe0fffacdb39218ac")
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .signWith(getSigningKey(secret)).compact();
        } catch (Exception e) {
            LOGGER.error("Error generating token: ", e);
            return null;
        }
    }

    public String getSubjectFromToken(String secret, String token){
        try {
            return Jwts.parser().verifyWith(getSigningKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            LOGGER.error("Error getting username from token: ", e);
            return null;
        }
    }

    public boolean validateToken(String secret,String token){
        try {
            Boolean expirationStatus = Jwts.parser().verifyWith(getSigningKey(secret)).build().parseClaimsJws(token)
                    .getBody().getExpiration().after(new Date(System.currentTimeMillis()));
            return expirationStatus && getSubjectFromToken(secret, token) != null;
        } catch (Exception e) {
            LOGGER.error("Error validating token: ", e);
            return false;
        }
    }

    private SecretKey getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}