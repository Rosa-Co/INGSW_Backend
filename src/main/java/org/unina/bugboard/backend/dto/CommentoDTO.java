package org.unina.bugboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoDTO {
    private Integer id;
    private String descrizione;
    private LocalDateTime data;
    private UtenteDTO scrittoDa;
    private Integer appartieneId;
}
