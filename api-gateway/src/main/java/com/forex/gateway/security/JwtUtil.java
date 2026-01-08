package com.forex.gateway.security;

import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtil {
	 private static final String SECRET = "ReplaceWithAStrongSecretKey123!"; // change in prod
	    private static final long EXP_MS = 1000 * 60 * 60 * 6; // 6 hours

	    public static String generateToken(String subject) {
	        return Jwts.builder()
	                .setSubject(subject)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
	                .signWith(SignatureAlgorithm.HS256, SECRET)
	                .compact();
	    }

	    public static Claims parseToken(String token) throws JwtException {
	        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	    }

	    public static String getSubject(String token) {
	        return getClaim(token, Claims::getSubject);
	    }

	    private static <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
	        Claims claims = parseToken(token);
	        return claimsResolver.apply(claims);
	    }

	    public static boolean isTokenValid(String token) {
	        try {
	            Claims c = parseToken(token);
	            return c.getExpiration().after(new Date());
	        } catch (JwtException e) {
	            return false;
	        }
	    }
	}