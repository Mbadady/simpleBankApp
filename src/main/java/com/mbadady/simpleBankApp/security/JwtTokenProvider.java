package com.mbadady.simpleBankApp.security;

import com.mbadady.simpleBankApp.customException.BankApiException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

//    generate Token

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + SecurityConstant.JWT_SECRET_EXPIRATION_IN_MILLISECONDS);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.JWT_SECRET_KEY)
                .compact();

        return token;
    }

//    get username from the token

    public String generateUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstant.JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

//    Validate token passed

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstant.JWT_SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch(SignatureException ex){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }catch(MalformedJwtException ex){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch(ExpiredJwtException ex){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch(UnsupportedJwtException ex){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }catch(IllegalArgumentException ex){
            throw new BankApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }
}
