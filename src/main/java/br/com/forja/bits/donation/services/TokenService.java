package br.com.forja.bits.donation.services;

import br.com.forja.bits.donation.enums.TokenTypes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    public String generate(TokenTypes type, String subject, long duration, String secret) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + duration);

        return Jwts.builder()
                .setSubject(subject)
                .setAudience(type.toString().toLowerCase())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public Optional<Claims> validate(String token, String secret) {
        if (token == null || token.isEmpty())
            return Optional.empty();

        try {
            return Optional.of(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody());
        } catch (Exception e) {
            System.out.println("Fail to validate token. error: " + e.getMessage());

            return Optional.empty();
        }
    }


}