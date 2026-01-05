package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.unina.bugboard.backend.model.enums.Role;

/**
 * Entità che rappresenta un utente del sistema.
 */
@Entity
@Table(name = "utente")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    /**
     * Identificativo univoco dell'utente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Indirizzo email dell'utente (usato per l'autenticazione).
     * Deve essere univoco.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Password dell'utente (cifrata).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Ruolo assegnato all'utente (es. USER, ADMIN).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false)
    private Role role = Role.USER;

}
