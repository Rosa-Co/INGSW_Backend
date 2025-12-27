package org.unina.bugboard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unina.bugboard.backend.model.Utente;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Optional<Utente> findByEmail(String email);
}
