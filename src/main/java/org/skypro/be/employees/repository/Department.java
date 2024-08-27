package org.skypro.be.employees.repository;

import org.springframework.stereotype.Component;

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

    public void setId(Long id) {
        this.id = id;
    }
}
