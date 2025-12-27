package org.unina.bugboard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unina.bugboard.backend.model.Commento;
import java.util.List;

public interface CommentoRepository extends JpaRepository<Commento, Integer> {
    List<Commento> findByAppartieneId(Integer issueId);
}
