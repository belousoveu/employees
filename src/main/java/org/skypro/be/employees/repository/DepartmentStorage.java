package org.skypro.be.employees.repository;

import jakarta.annotation.PostConstruct;
import org.skypro.be.employees.entity.Department;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DepartmentStorage implements Repository<Department> {
    private final static Map<Integer, Department> departments = new HashMap<>();
    private int currentId = 0;


    @PostConstruct
    public void init() {
        List<String> departmentsNames = List.of("Администрация", "Отдел разработки", "Отдел продаж", "Отдел сопровождения");
        for (String name : departmentsNames) {
            Department department = new Department();
            department.setId(++currentId);
            department.setName(name);
            departments.put(department.getId(), department);
        }
    }

    public int getNextId() {
        return ++currentId;
    }

    @Override
    public Department save(Department department) {
        return departments.put(department.getId(), department);
    }

    @Override
    public Department delete(Department department) {
        return departments.remove(department.getId());
    }

    @Override
    public Collection<Department> findAll() {
        return departments.values().stream().toList();
    }

    @Override
    public Optional<Department> findById(int id) {
        return departments.entrySet().stream()
                .filter(entry -> entry.getKey().equals(id))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    @Override
    public Optional<Department> findByFirstNameAndLastName(String firstName, String lastName) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
