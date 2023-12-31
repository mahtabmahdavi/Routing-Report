package com.hammasir.routingreport.model.enumuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Police {

    POLICE("POLICE"),
    SECRET_POLICE("SECRET_POLICE"),
    OPPOSITE_LINE("OPPOSITE_LINE");

    private final String value;

    public static Police fromValue(String value) {
        for (Police category : Police.values()) {
            if (category.getValue().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid Police Category value: " + value);
    }
}
