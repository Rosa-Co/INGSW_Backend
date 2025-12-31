package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.dto.LoginRequest;
import org.unina.bugboard.backend.dto.LoginResponse;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.security.JwtUtils;
import org.unina.bugboard.backend.service.UtenteService;
import org.unina.bugboard.backend.service.impl.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UtenteController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    JwtUtils jwtUtils;

    // Build-in simple mapper for now
    private UtenteDTO mapToDTO(Utente utente) {
        return new UtenteDTO(utente.getId(), utente.getEmail(), utente.getIsAdmin());
    }

    @GetMapping
    public List<UtenteDTO> getAllUsers() {
        return utenteService.getAllUsers().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getUserById(@PathVariable Integer id) {
        return utenteService.getUserById(id)
                .map(u -> ResponseEntity.ok(mapToDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtenteDTO> createUser(@RequestBody Utente utente) {
        // NOTE: Accepting Entity here for Password. Ideally should use UserCreationDTO
        Utente created = utenteService.createUser(utente);
        return ResponseEntity.ok(mapToDTO(created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtenteDTO> updateUser(@PathVariable Integer id, @RequestBody Utente utenteDetails) {
        try {
            Utente updated = utenteService.updateUser(id, utenteDetails);
            return ResponseEntity.ok(mapToDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        utenteService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }
}
