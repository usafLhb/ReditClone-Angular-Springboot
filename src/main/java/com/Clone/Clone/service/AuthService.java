package com.Clone.Clone.service;

import com.Clone.Clone.dto.*;
import com.Clone.Clone.exception.SpringRedditException;
import com.Clone.Clone.model.*;
import com.Clone.Clone.repository.UserRepository;
import com.Clone.Clone.repository.VerificationTokenRepository;
import com.Clone.Clone.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.security.Security;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
     private final PasswordEncoder passwordEncoder;
     private final UserRepository userRepository;
     private final VerificationTokenRepository verificationTokenRepository;
     private final MailService mailService;
     private final AuthenticationManager authenticationManager;
     private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    @Transactional
    public void signup(RegisterRequest registerRequest) throws SpringRedditException {
        User user=new  User();
        System.out.println(registerRequest.toString());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnable(false);
        userRepository.save(user);
        System.out.println("User  est "+user.toString());
        String token=generateVerificationToken(user);
//        mailService.sendMail(new NotificationEmail("Please active ur  account",user.getEmail(),"Thank  u for  singing up Youssef LHB project "+token ));
    }
    private String generateVerificationToken(User user){
        String token= UUID.randomUUID().toString();
        System.out.println("tooookkkeeennn"+token);
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
}

    public void verifyAccount(String token) throws SpringRedditException {
        Optional<VerificationToken> verificationToken= verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username=verificationToken.getUser().getUsername();
        try {
          User user=  userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("user not found "+username));
            user.setEnable(true);
            userRepository.save (user);
        } catch (SpringRedditException e) {
            throw new RuntimeException(e);
        }
    }
    public  AuthenticationResponse login (LoginRequest loginRequest){
        System.out.println("securite  1 +"+loginRequest.getUserName()+" + "+loginRequest.getPassword());
      Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
        System.out.println("securite  2 "+authenticate);
      SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token=jwtProvider.generateToken(authenticate);
        System.out.println("securite  3 "+token);
        return  AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUserName())
                .build();
    }
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws SpringRedditException {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
    @Transactional
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("@# User name not found - " + principal.getUsername()));
    }
}

