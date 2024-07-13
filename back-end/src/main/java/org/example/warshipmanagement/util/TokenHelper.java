package org.example.warshipmanagement.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class TokenHelper {
    public static String generateToken(Map claims) {
        return JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30))
                .sign(Algorithm.HMAC256("SunSeaLuckyGonnaHere"));
    }

    public static String parseToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("SunSeaLuckyGonnaHere")).build();
        return jwtVerifier.verify(token).getClaims().get("user").toString();
    }

}
