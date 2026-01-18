package org.unina.bugboard.backend.model.enums;

public enum IssueSortField {
    TIPOLOGIA("tipologia"),
    STATO("stato"),
    PRIORITA("priorita");

    private final String fieldName;

    IssueSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }
}
