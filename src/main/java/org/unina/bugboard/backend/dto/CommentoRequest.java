package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Oggetto richiesta per la creazione o modifica di un commento.
 * Contiene i dati necessari inviati dal client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoRequest {
    /**
     * Testo del commento. Non può essere vuoto.
     */
    @NotBlank(message = "Description cannot be empty")
    private String descrizione;

    /**
     * ID dell'issue a cui aggiungere il commento. Obbligatorio.
     */
    @NotNull(message = "Issue ID is mandatory")
    private Integer appartieneId;
}
