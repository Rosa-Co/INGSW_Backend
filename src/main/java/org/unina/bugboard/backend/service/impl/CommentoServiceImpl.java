package org.unina.bugboard.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unina.bugboard.backend.model.Commento;
import org.unina.bugboard.backend.repository.CommentoRepository;
import org.unina.bugboard.backend.service.CommentoService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentoServiceImpl implements CommentoService {

    private final CommentoRepository commentoRepository;

    @Autowired
    public CommentoServiceImpl(CommentoRepository commentoRepository) {
        this.commentoRepository = commentoRepository;
    }

    @Override
    public List<Commento> getAllComments() {
        return commentoRepository.findAll();
    }

    @Override
    public List<Commento> getCommentsByIssueId(Integer issueId) {
        return commentoRepository.findByAppartieneId(issueId);
    }

    @Override
    public Optional<Commento> getCommentById(Integer id) {
        return commentoRepository.findById(id);
    }

    @Override
    public Commento createComment(Commento commento) {
        return commentoRepository.save(commento);
    }

}
