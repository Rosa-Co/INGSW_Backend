package org.unina.bugboard.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentoRequest {
    @NotBlank(message = "Description cannot be empty")
    private String descrizione;

    @NotNull(message = "Issue ID is mandatory")
    private Integer appartieneId;
}
