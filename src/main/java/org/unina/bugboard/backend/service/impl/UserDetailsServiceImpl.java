package org.unina.bugboard.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.repository.UtenteRepository;
import org.unina.bugboard.backend.security.UserDetailsImpl;

/**
 * Implementazione del servizio UserDetailsService di Spring Security.
 * Gestisce il caricamento dei dettagli dell'utente per l'autenticazione.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    @Autowired
    public UserDetailsServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * Carica i dettagli dell'utente basandosi sull'username (email).
     * Utilizzato da Spring Security durante il processo di autenticazione.
     *
     * @param email L'email dell'utente da caricare.
     * @return Un oggetto UserDetails contenente le informazioni dell'utente.
     * @throws UsernameNotFoundException Se l'utente non viene trovato con l'email
     *                                   fornita.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(utente);
    }
}
