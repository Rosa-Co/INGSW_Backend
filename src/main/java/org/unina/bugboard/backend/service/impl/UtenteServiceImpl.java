package org.unina.bugboard.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.repository.UtenteRepository;
import org.unina.bugboard.backend.service.UtenteService;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public List<Utente> getAllUsers() {
        return utenteRepository.findAll();
    }

    @Override
    public Optional<Utente> getUserById(Integer id) {
        return utenteRepository.findById(id);
    }

    @Override
    public Optional<Utente> getUserByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    @Override
    public Utente createUser(Utente utente) {
        return utenteRepository.save(utente);
    }

}
