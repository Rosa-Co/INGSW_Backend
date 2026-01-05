package org.unina.bugboard.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String email;
    private List<String> roles;

    public LoginResponse(String accessToken, Integer id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}
