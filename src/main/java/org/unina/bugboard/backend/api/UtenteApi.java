package org.unina.bugboard.backend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.LoginRequest;
import org.unina.bugboard.backend.dto.UserCreationRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.model.Utente;

import jakarta.validation.Valid;
import java.util.List;

@RequestMapping("/api/users")
public interface UtenteApi {

    @GetMapping
    List<UtenteDTO> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<UtenteDTO> getUserById(@PathVariable Integer id);

    @GetMapping("/email/{email}")
    ResponseEntity<UtenteDTO> getUserByEmail(@PathVariable String email);

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UtenteDTO> createUser(
            @RequestBody @Valid org.unina.bugboard.backend.dto.UserCreationRequest userCreationRequest);
}
