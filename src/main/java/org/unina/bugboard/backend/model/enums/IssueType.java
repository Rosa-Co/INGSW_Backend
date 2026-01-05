package org.unina.bugboard.backend.model.enums;

/**
 * Enum che definisce le tipologie di segnalazione.
 */
public enum IssueType {
    /**
     * Richiesta di chiarimento o domanda generale.
     */
    QUESTION,
    /**
     * Segnalazione di un malfunzionamento o errore.
     */
    BUG,
    /**
     * Richiesta o modifica relativa alla documentazione.
     */
    DOCUMENTATION,
    /**
     * Richiesta di una nuova funzionalità.
     */
    FEATURE
}
