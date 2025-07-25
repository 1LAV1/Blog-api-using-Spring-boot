package com.lv.blogapp.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {



    private String secretkey="";

    public JwtService() {
        try {
            KeyGenerator keygen= KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk=keygen.generateKey();
            secretkey =Base64.getEncoder().encodeToString(sk.getEncoded());    
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();

        }


    }

    public String generatetoken(String username) {
        
      Map<String ,Object> Claims = new HashMap<>();

      return Jwts.builder().
                claims()
                .add(Claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*30))
                .and()
                .signWith(getkey())
                .compact(); 
      
    }
    private SecretKey getkey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);

    }
    
    public String extractUsername(String jwtToken) {
        // Extracts the username from the JWT token
        // This method assumes that the JWT token is valid and has been signed with the secret key
      return extractClaims(jwtToken,Claims::getSubject); 
    }



    private <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
    
    final Claims claims = extractAllClaims(jwtToken);
    return claimsResolver.apply(claims);}  




    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}
