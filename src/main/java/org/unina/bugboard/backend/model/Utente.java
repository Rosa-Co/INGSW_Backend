package org.unina.bugboard.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.unina.bugboard.backend.model.enums.Role;

@Entity
@Table(name = "utente")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false)
    private Role role = Role.USER;

}
