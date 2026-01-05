package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unina.bugboard.backend.model.enums.IssuePriority;
import org.unina.bugboard.backend.model.enums.IssueStatus;
import org.unina.bugboard.backend.model.enums.IssueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String titolo;

    @NotBlank(message = "Description cannot be empty")
    private String descrizione;

    @NotNull(message = "Type is mandatory")
    private IssueType tipologia;

    private String img;

    @NotNull(message = "Priority is mandatory")
    private IssuePriority priorita;

    private IssueStatus stato; // Optional for update
}
