package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

/**
 * Oggetto richiesta per la creazione o modifica di un'issue.
 * Contiene i dati necessari inviati dal client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    /**
     * Titolo dell'issue. Non può essere vuoto e deve avere una lunghezza specifica.
     */
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String titolo;

    /**
     * Descrizione dell'issue. Non può essere vuota.
     */
    @NotBlank(message = "Description cannot be empty")
    private String descrizione;

    /**
     * Tipo di issue (es. BUG, FEATURE). Obbligatorio.
     */
    @NotNull(message = "Type is mandatory")
    private IssueType tipologia;

    /**
     * URL o percorso opzionale di un'immagine allegata.
     */
    private String img;

    /**
     * Livello di priorità. Obbligatorio.
     */
    @NotNull(message = "Priority is mandatory")
    private IssuePriority priorita;

    /**
     * Stato dell'issue. Opzionale in fase di aggiornamento.
     */
    private IssueStatus stato; // Optional for update
}
