package org.unina.bugboard.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) per rappresentare un commento.
 * Contiene le informazioni di un commento da restituire al client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoDTO {
    /**
     * Identificativo univoco del commento.
     */
    private Integer id;

    /**
     * Testo del commento.
     */
    private String descrizione;

    /**
     * Data e ora di creazione del commento.
     */
    private LocalDateTime data;

    /**
     * DTO dell'utente che ha scritto il commento.
     */
    private UtenteDTO scrittoDa;

    /**
     * ID dell'issue a cui il commento appartiene.
     */
    private Integer appartieneId;
}
