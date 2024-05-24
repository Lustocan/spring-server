package it.example.lavoretti.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtService {
    // Our secret, everytime that the server it's restated or goes down, creates a new one
    private String SECRET = RandomStringUtils.randomAlphanumeric(25);

    /* The Claim is a field contained into a Jwt, this method takes a String and
       a Function, we use this method for return useful data that refers to the claim requested.
    */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // This method return a new Key using the Secret and the Sha encryption algorithm
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // This method returns all the claims contained in the Jwt.
    private Claims extractAllClaims(String token){
        return Jwts.
               parserBuilder().
               setSigningKey(getSignKey()).
               build().
               parseClaimsJws(token).
               getBody();
    }

    // Useful method to create a token.
    public String createToken(Map<String, Object> claims, String username){
        return Jwts.builder().
               setClaims(claims).
               setSubject(username).
               setIssuedAt(new Date(System.currentTimeMillis())).
               setExpiration(new Date(System.currentTimeMillis()+(1000*60*60))).
               signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    // Here we use createToken method to actually generate a new token.
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /* This method define the second argument of the extractClaim method defined above,
       how you can see in that method there's a function that takes two
       parameters, the first one it's a String and the second it's a generic.
       Here we return a subject that is a String. */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /* This method define the second argument of the extractClaim method defined above,
       how you can see in that method there's a function that takes two
       parameters, the first one it's a String and the second it's a generic.
       Here we return an Expiration that it's represented with a Date Object. */
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // If the current date is after the expiration date return false, otherwise return true.
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // If the token is valid return true, otherwise return false.
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
