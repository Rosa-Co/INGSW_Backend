package org.unina.bugboard.backend.dto;

public class UtenteDTO {
    private Integer id;
    private String email;
    private String role;

    public UtenteDTO() {
    }

    public UtenteDTO(Integer id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
