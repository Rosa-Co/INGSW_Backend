package org.unina.bugboard.backend.service;

import org.unina.bugboard.backend.model.Commento;

import java.util.List;
import java.util.Optional;

public interface CommentoService {
    List<Commento> getAllComments();

    List<Commento> getCommentsByIssueId(Integer issueId);

    Optional<Commento> getCommentById(Integer id);

    Commento createComment(Commento commento);

}
