package com.Clone.Clone.security;

import com.Clone.Clone.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

@Service
public class JwtProvider {
    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;


//    Login
    @PostConstruct
    public void init() throws SpringRedditException {
        try{
            keyStore=keyStore.getInstance("JKS");
            InputStream resourceAsStream=getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream,"secret".toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new SpringRedditException("Exception occured while loading keyStore");
        }
    }
    private PrivateKey getPrivateKey(){
        try {
            return (PrivateKey) keyStore.getKey("springblog","secret".toCharArray());
        } catch (KeyStoreException |NoSuchAlgorithmException | UnrecoverableKeyException e) {
            try {
                throw new SpringRedditException("Exception occured while retrieving public key from keystore");
            } catch (SpringRedditException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public String generateToken(Authentication authentication){
      org.springframework.security.core.userdetails.User pricipal= (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(pricipal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }
// Fin login
//Filter
    public boolean validateToken(String jwt){
        try {
            parser().setSigningKey(getpublickey()).parseClaimsJws(jwt);
            return true;
        } catch (SpringRedditException e) {
            throw new RuntimeException(e);
        }
    }

    private PublicKey getpublickey() throws SpringRedditException {
        try{
            return  keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Expectation  occured while retrieving public key");
        }
    }
    public String getUsernameFromJwt(String token) throws SpringRedditException {
        Claims claims=parser()
                .setSigningKey(getpublickey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
//    Fin FILTER

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

}
