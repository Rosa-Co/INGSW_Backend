package org.unina.bugboard.backend.api;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interfaccia API per la gestione delle immagini.
 */
@RequestMapping("/api/images")
public interface ImageApi {

    /**
     * Carica un'immagine per una issue.
     *
     * @param issueId l'ID dell'issue
     * @param file    il file dell'immagine
     * @return il percorso dell'immagine salvata
     * @throws IOException in caso di errore I/O
     */
    @PostMapping(value = "/upload/{issueId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadImage(@PathVariable Integer issueId, @RequestParam("file") MultipartFile file)
            throws IOException;

    /**
     * Recupera un'immagine dal server.
     *
     * @param filename il nome del file
     * @return la risorsa dell'immagine
     * @throws IOException in caso di errore I/O
     */
    @GetMapping("/{filename:.+}")
    ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException;
}
