package org.unina.bugboard.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestore globale delle eccezioni per l'applicazione.
 * Intercetta le eccezioni lanciate dai controller e restituisce risposte HTTP
 * appropriate.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR = "error";

    /**
     * Gestisce le eccezioni di validazione degli argomenti del metodo (es. @Valid
     * fallito).
     *
     * @param ex l'eccezione MethodArgumentNotValidException
     * @return una mappa contenente i nomi dei campi non validi e i relativi
     *         messaggi di errore
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestisce l'eccezione FileNotFoundException.
     *
     * @param ex l'eccezione FileNotFoundException
     * @return una risposta con messaggio di errore e stato 404 Not Found
     */
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFileNotFoundException(FileNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Gestisce l'eccezione IOException.
     *
     * @param ex l'eccezione IOException
     * @return una risposta con messaggio di errore e stato 500 Internal Server
     *         Error
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR, "I/O Error: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gestisce errori di binding dei parametri di richiesta
     * (es. enum non validi nei @RequestParam).
     *
     * Caso tipico:
     *  /api/issues?tipologia=NOT_A_TYPE
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleEnumBindingError(
            MethodArgumentTypeMismatchException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("error", "Invalid request parameter");
        body.put("parameter", ex.getName());
        body.put("value", ex.getValue());
        body.put("expectedType", ex.getRequiredType() != null
                ? ex.getRequiredType().getSimpleName()
                : "unknown");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    /**
     * Gestore generico per tutte le altre eccezioni non gestite specificamente.
     *
     * @param ex l'eccezione generica Exception
     * @return una risposta con messaggio di errore e stato 500 Internal Server
     *         Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
