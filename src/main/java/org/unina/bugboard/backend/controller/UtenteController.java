package org.unina.bugboard.backend.controller;

import jakarta.validation.Valid;
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
import org.unina.bugboard.backend.mapper.UtenteMapper;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.security.JwtUtils;
import org.unina.bugboard.backend.security.UserDetailsImpl;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UtenteController {

    private final UtenteService utenteService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UtenteMapper utenteMapper;

    @Autowired
    public UtenteController(UtenteService utenteService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            UtenteMapper utenteMapper) {
        this.utenteService = utenteService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.utenteMapper = utenteMapper;
    }

    @GetMapping
    public List<UtenteDTO> getAllUsers() {
        return utenteService.getAllUsers().stream()
                .map(utenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getUserById(@PathVariable Integer id) {
        return utenteService.getUserById(id)
                .map(utenteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UtenteDTO> getUserByEmail(@PathVariable String email) {
        return utenteService.getUserByEmail(email)
                .map(utenteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtenteDTO> createUser(@RequestBody Utente utente) {
        // NOTE: Accepting Entity here for Password. Ideally should use UserCreationDTO
        Utente created = utenteService.createUser(utente);
        return ResponseEntity.ok(utenteMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtenteDTO> updateUser(@PathVariable Integer id, @RequestBody Utente utenteDetails) {
        try {
            Utente updated = utenteService.updateUser(id, utenteDetails);
            return ResponseEntity.ok(utenteMapper.toDTO(updated));
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
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
