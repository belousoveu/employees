package org.skypro.be.employees.repository;

public enum Gender {
    MALE("Муж."),
    FEMALE("Жен.");

    private final String value;

    Gender(String gender) {
        this.value = gender;
    }
//
//    public String getName() {
//        return this.name();
//    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
