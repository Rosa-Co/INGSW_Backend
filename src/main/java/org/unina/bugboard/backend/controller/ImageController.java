package org.unina.bugboard.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.unina.bugboard.backend.api.ImageApi;
import org.unina.bugboard.backend.service.IssueService;

import java.io.IOException;

/**
 * Controller per la gestione delle immagini.
 * Fornisce endpoint per caricare e recuperare le immagini associate alle issue.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController implements ImageApi {

    private final IssueService issueService;

    /**
     * Costruttore per l'iniezione delle dipendenze.
     *
     * @param issueService servizio per la gestione delle issue e dei file
     */
    @Autowired
    public ImageController(IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * Carica un'immagine associandola a una issue.
     *
     * @param issueId l'ID dell'issue a cui associare l'immagine
     * @param file    il file dell'immagine da caricare
     * @return 200 OK con il percorso dell'immagine salvata
     * @throws IOException se si verificano errori durante il salvataggio del file
     */
    @Override
    public ResponseEntity<String> uploadImage(Integer issueId, MultipartFile file) throws IOException {
        String path = issueService.uploadImage(issueId, file);
        return ResponseEntity.ok(path);
    }

    /**
     * Recupera un'immagine tramite il nome del file.
     *
     * @param filename il nome del file dell'immagine
     * @return 200 OK con il contenuto dell'immagine
     * @throws IOException se si verificano errori durante il recupero del file
     */
    @Override
    public ResponseEntity<Resource> getImage(String filename) throws IOException {
        Resource resource = issueService.loadImage(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
