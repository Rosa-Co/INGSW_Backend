package org.unina.bugboard.backend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unina.bugboard.backend.model.Issue;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByStato(String stato);

    List<Issue> findByPriorita(String priorita);

    List<Issue> findByCreataDaId(Integer userId);

    @Query("SELECT i FROM Issue i WHERE " +
            "(:tipologia IS NULL OR i.tipologia = :tipologia) AND " +
            "(:stato IS NULL OR i.stato = :stato) AND " +
            "(:priorita IS NULL OR i.priorita = :priorita)")
    List<Issue> findIssuesWithFilters(@Param("tipologia") IssueType tipologia,
            @Param("stato") IssueStatus stato,
            @Param("priorita") IssuePriority priorita,
            Sort sort);
}
