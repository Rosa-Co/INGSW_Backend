package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.unina.bugboard.backend.api.UtenteApi;
import org.unina.bugboard.backend.dto.UserCreationRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.mapper.UtenteMapper;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UtenteController implements UtenteApi {

    private final UtenteService utenteService;
    private final UtenteMapper utenteMapper;

    @Autowired
    public UtenteController(UtenteService utenteService,
            UtenteMapper utenteMapper) {
        this.utenteService = utenteService;
        this.utenteMapper = utenteMapper;
    }

    @Override
    public List<UtenteDTO> getAllUsers() {
        return utenteService.getAllUsers().stream()
                .map(utenteMapper::toDTO)
                .toList();
    }

    @Override
    public ResponseEntity<UtenteDTO> getUserById(Integer id) {
        return utenteService.getUserById(id)
                .map(utenteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteDTO> getUserByEmail(String email) {
        return utenteService.getUserByEmail(email)
                .map(utenteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<UtenteDTO> createUser(UserCreationRequest userCreationRequest) {
        Utente utenteToCreate = utenteMapper.toEntity(userCreationRequest);
        Utente created = utenteService.createUser(utenteToCreate);
        return ResponseEntity.ok(utenteMapper.toDTO(created));
    }
}
