package com.arslankucukkafa.labormarketauth.idm.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;

@Service
public class JwtService implements Serializable {

    private final String secret;
    private final long tokenValidty;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.token.validity}") long tokenValidty) {
        this.secret = secret;
        this.tokenValidty = tokenValidty;
    }

    public String generateToken(Authentication authentication){
        var role = authentication.getAuthorities();
        return Jwts.builder().subject(authentication.getName()).expiration(
                        new Date(System.currentTimeMillis()+1000*tokenValidty))
                .issuer("604ef5b2eea6b60a8dc809bfe0fffacdb39218ac")
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey()).compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().decryptWith(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean tokenValidate(String token){
        Boolean exprationStatus = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token)
                .getBody().getExpiration().after(new Date(System.currentTimeMillis()));
        if (exprationStatus && getUsernameFromToken(token) != null) {
            return true;
        }
        return false;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
