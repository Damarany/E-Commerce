package com.ARD.eCommerce.configurations.security.JWT;

import com.ARD.eCommerce.dtos.AuthDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtilty {
    Map<String, Object> claimsMap;

    public String generateToken(AuthDto authDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstance.JWT_USER_NAME_CLAIM_NAME, authDto.getUsername());
        claims.put(JwtConstance.JWT_USER_MAIL_CLAIM_NAME, authDto.getEmail());
        claims.put(JwtConstance.JWT_USER_ID_CLAIM_NAME, authDto.getUserId());
        claims.put(JwtConstance.JWT_USER_ROLE_CLAIM_NAME, authDto.getRoles());
        claims.put(JwtConstance.JWT_USER_PERMISSION_CLAIM_NAME, authDto.getPermissions());
        return CreateToken(claims, authDto.getEmail());
    }

    public String CreateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstance.JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JwtConstance.JWT_SECRET_KEY)
                .compact();
    }

    public Map<String, Object> getAllClaimsFromToken(String token) {
        claimsMap = Jwts.parserBuilder()
                .setSigningKey(JwtConstance.JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsMap;
    }

    //get token from request
    public String getTokenFromRequest(HttpServletRequest request){
        String bearToken = request.getHeader(JwtConstance.HEADER);
        if (StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer ")){
            return bearToken.substring(7,bearToken.length());
        }
        return null;
    }

    //get username from token
    public String getUsernameFromClaims(String token) {
        claimsMap = getAllClaimsFromToken(token);
        return (String) claimsMap.get(JwtConstance.JWT_USER_NAME_CLAIM_NAME);
    }


    //get email from token
    public String getEmailFromClaims(String token) {
        claimsMap = getAllClaimsFromToken(token);
        return (String) claimsMap.get(JwtConstance.JWT_USER_MAIL_CLAIM_NAME);
    }

    //get userId from token
    public Long getUserIdFromClaims(String token) {
        claimsMap = getAllClaimsFromToken(token);
        return Long.parseLong(claimsMap.get(JwtConstance.JWT_USER_ID_CLAIM_NAME).toString());
    }

    //validate token
    public boolean validateToken(String token) {
        try {
            Jwts.
                    parserBuilder().
                    setSigningKey(JwtConstance.JWT_SECRET_KEY).
                    build().
                    parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException("JWT was EXPIRED or incorrect");
        }

    }

}
