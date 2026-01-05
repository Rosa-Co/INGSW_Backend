package org.unina.bugboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {
    private Integer id;
    private String email;
    private Role role;
}
