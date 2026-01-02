package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Utente;
import java.util.List;
import java.util.Optional;

public interface UtenteService {
    List<Utente> getAllUsers();

    Optional<Utente> getUserById(Integer id);

    Optional<Utente> getUserByEmail(String email);

    Utente createUser(Utente utente);

}
