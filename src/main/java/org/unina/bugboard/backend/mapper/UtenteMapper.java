package org.unina.bugboard.backend.mapper;

import org.springframework.stereotype.Component;
import org.unina.bugboard.backend.dto.UserCreationRequest;
import org.unina.bugboard.backend.dto.UtenteDTO;
import org.unina.bugboard.backend.model.Utente;

/**
 * Componente responsabile della mappatura tra l'entità Utente e i relativi DTO.
 */
@Component
public class UtenteMapper {

    /**
     * Converte un'entità Utente in un UtenteDTO.
     *
     * @param utente l'entità Utente da convertire
     * @return l'UtenteDTO corrispondente, o null se l'input è null
     */
    public UtenteDTO toDTO(Utente utente) {
        if (utente == null) {
            return null;
        }
        return new UtenteDTO(
                utente.getId(),
                utente.getEmail(),
                utente.getRole());
    }

    /**
     * Converte una richiesta di creazione utente (UserCreationRequest) in un'entità
     * Utente.
     * Nota: la password dovrebbe essere hashata prima di salvare l'entità
     * persistente.
     *
     * @param request l'oggetto contenente i dati per la creazione dell'utente
     * @return l'entità Utente popolata, o null se l'input è null
     */
    public Utente toEntity(UserCreationRequest request) {
        if (request == null) {
            return null;
        }
        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setPassword(request.getPassword()); // Ideally hashed here or in service
        utente.setRole(request.getRole());

        return utente;
    }
}
