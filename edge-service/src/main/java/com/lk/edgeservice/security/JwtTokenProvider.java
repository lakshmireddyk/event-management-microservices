package com.lk.edgeservice.security;

import com.lk.edgeservice.model.Account;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider implements Serializable {

    private final static int ACCESS_TOKEN_VALIDITY_MILLIS  =   6000000;

    @Value("${jwt.security.key}")
    private String jwtKey;
    public String generateToken(Account account) {
        Claims claims = Jwts.claims().setSubject(account.getEmail());

        claims.put("role",account.getRole());
        claims.put("userId",account.getUserId());
        claims.put("email",account.getEmail());
        claims.put("password",account.getPassword());

        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date issueDate  =   new Date(t);
        Date expiryDate=new Date(t + (60 * ACCESS_TOKEN_VALIDITY_MILLIS));

        System.out.format("=====ISSUE DATE %s and EXP DATE %s ====",issueDate,expiryDate);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://localhost")
                .setIssuedAt(issueDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {

        } catch (MalformedJwtException ex) {
        } catch (ExpiredJwtException ex) {
        } catch (UnsupportedJwtException ex) {
        } catch (IllegalArgumentException ex) {
        }
        return false;
    }

    public Claims getUsernameFromToken(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

}
