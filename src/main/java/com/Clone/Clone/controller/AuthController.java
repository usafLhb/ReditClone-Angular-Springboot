package com.Clone.Clone.controller;

import com.Clone.Clone.dto.AuthenticationResponse;
import com.Clone.Clone.dto.LoginRequest;
import com.Clone.Clone.dto.RefreshTokenRequest;
import com.Clone.Clone.dto.RegisterRequest;
import com.Clone.Clone.exception.SpringRedditException;
import com.Clone.Clone.service.AuthService;
import com.Clone.Clone.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) throws SpringRedditException {
        authService.signup(registerRequest);
        System.out.println("name a ucf "+registerRequest.toString());

        return new ResponseEntity<>("user Registration successful", HttpStatus.OK);
    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) throws SpringRedditException {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated",HttpStatus.OK);
    }
    @PostMapping("/login")
        public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)  {
        return authService.login(loginRequest);
    }
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws SpringRedditException {
        return authService.refreshToken(refreshTokenRequest);
    }
//
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws SpringRedditException {
        System.out.println("Refresh token is  "+refreshTokenRequest.getRefreshToken());
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("Refresh Token Deleted Successfully!!",HttpStatus.OK);
    }

}
