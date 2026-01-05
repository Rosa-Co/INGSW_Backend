package org.unina.bugboard.backend.model.enums;

/**
 * Enum che definisce gli stati possibili di una segnalazione.
 */
public enum IssueStatus {
    /**
     * La segnalazione è stata creata ma non ancora presa in carico.
     */
    TODO,
    /**
     * La segnalazione è in fase di lavorazione.
     */
    IN_PROGRESS,
    /**
     * La segnalazione è stata risolta o completata.
     */
    DONE
}
