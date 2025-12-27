package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UtenteController {

    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public List<Utente> getAllUsers() {
        return utenteService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUserById(@PathVariable Integer id) {
        return utenteService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utente createUser(@RequestBody Utente utente) {
        return utenteService.createUser(utente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utente> updateUser(@PathVariable Integer id, @RequestBody Utente utenteDetails) {
        try {
            return ResponseEntity.ok(utenteService.updateUser(id, utenteDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        utenteService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utente loginRequest) {
        return utenteService.getUserByEmail(loginRequest.getEmail())
                .filter(u -> u.getPassword().equals(loginRequest.getPassword())) // Plain text check for now
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
