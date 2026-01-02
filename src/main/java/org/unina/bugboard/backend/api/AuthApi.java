package org.unina.bugboard.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unina.bugboard.backend.dto.LoginRequest;

@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest);
}
