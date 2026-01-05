package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unina.bugboard.backend.api.AuthApi;
import org.unina.bugboard.backend.dto.LoginRequest;
import org.unina.bugboard.backend.dto.LoginResponse;
import org.unina.bugboard.backend.security.JwtUtils;
import org.unina.bugboard.backend.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
// @CrossOrigin removed
public class AuthController implements AuthApi {

        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;

        @Autowired
        public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
                this.authenticationManager = authenticationManager;
                this.jwtUtils = jwtUtils;
        }

        @Override
        public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getPassword()));

                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList();

                // ? good
                return ResponseEntity.ok(
                                new LoginResponse(
                                                jwt,
                                                userDetails.getId(),
                                                userDetails.getEmail(),
                                                roles));
        }
}
