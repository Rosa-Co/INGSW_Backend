package org.unina.bugboard.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unina.bugboard.backend.model.Issue;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByStato(String stato);

    List<Issue> findByPriorita(String priorita);

    List<Issue> findByCreataDaId(Integer userId);
}
