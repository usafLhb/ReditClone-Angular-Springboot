package com.Clone.Clone.service;

import com.Clone.Clone.exception.SpringRedditException;
import com.Clone.Clone.model.RefreshToken;
import com.Clone.Clone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }
    void validateRefreshToken(String token) throws SpringRedditException {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
    }
    String validateTokenDelete(String token) throws SpringRedditException {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid Token"));
        return token;
    }
    public void deleteRefreshToken(String token) throws SpringRedditException {
     String valideToken= validateTokenDelete(token);
     if(valideToken!=null){
        refreshTokenRepository.deleteByToken(token);}
    }
}
