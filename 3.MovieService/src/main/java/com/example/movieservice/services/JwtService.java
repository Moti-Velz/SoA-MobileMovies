package com.example.movieservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET_KEY = "toto";
    private final Algorithm algorithm;

    public JwtService() {
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    public boolean validate(String token) {
        try {
            // Verify the token
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

            // Print the claims
            Map<String, Claim> claims = jwt.getClaims();
            System.out.println("Claims in the JWT: " + claims);
            return true;
        } catch (JWTDecodeException ex) {
            System.out.println("Invalid JWT token: " + ex.getMessage());
            return false;
        }
    }
}






