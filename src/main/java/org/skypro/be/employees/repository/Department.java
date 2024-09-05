package org.skypro.be.employees.repository;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Department {
    private static Long currentId = 0L;
    private Long id;
    private String name;


    public Department() {
    }

    public Department(String name) {
        this.id = ++currentId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
