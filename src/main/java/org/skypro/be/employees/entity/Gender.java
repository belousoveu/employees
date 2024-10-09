package org.skypro.be.employees.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Муж."),
    FEMALE("Жен.");

    private final String value;

    Gender(String gender) {
        this.value = gender;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
