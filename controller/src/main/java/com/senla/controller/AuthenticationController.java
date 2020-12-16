package com.senla.controller;

import com.senla.dto.AuthenticationRequestDto;
import com.senla.entity.User;
import com.senla.security.JwtTokenProvider;
import com.senla.service.token.TokenService;
import com.senla.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * Login
     *
     * @param requestDto request dto
     * @return response as a string
     */
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            User user = userService.findUserByUserName(username);
            if (user.getUserName() == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            } else {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
                String token = jwtTokenProvider.createToken(username, user.getRoles());
                Map<Object, Object> response = new HashMap<>();
                response.put("username", username);
                response.put("token", token);

                return ResponseEntity.ok(response);
            }

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    /**
     * Log out
     *
     * @param authorization authorization number
     * @return response as a string
     */
    @GetMapping("/exit")
    public ResponseEntity logout(@RequestHeader(name = "Authorization") String authorization) {
        if (authorization != null && authorization.startsWith("Bearer_")) {
            String token = authorization.substring(7, authorization.length());
            tokenService.addToken(token);
            log.info("You are logged out");
        }
        return ResponseEntity.ok()
                .body("You are logged out");
    }
}
