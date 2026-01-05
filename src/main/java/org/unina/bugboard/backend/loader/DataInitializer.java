package org.unina.bugboard.backend.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.model.Utente;
import org.unina.bugboard.backend.model.enums.Role;
import org.unina.bugboard.backend.repository.UtenteRepository;

/**
 * Componente responsabile dell'inizializzazione dei dati all'avvio
 * dell'applicazione.
 * In particolare, si occupa di creare un utente amministratore se non ne esiste
 * già uno.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${bugboard.admin.email}")
    private String adminEmail;

    @Value("${bugboard.admin.password}")
    private String adminPassword;

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Metodo eseguito all'avvio dell'applicazione.
     * Controlla se esiste un utente con l'email amministratore specificata nella
     * configurazione.
     * Se non esiste, crea un nuovo utente con ruolo ADMIN e la password
     * specificata.
     *
     * @param args argomenti della riga di comando (non utilizzati)
     * @throws Exception se si verifica un errore durante l'esecuzione
     */
    @Override
    public void run(String... args) throws Exception {
        if (utenteRepository.findByEmail(adminEmail).isEmpty()) {
            Utente admin = new Utente();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);

            utenteRepository.save(admin);
        }
    }
}
