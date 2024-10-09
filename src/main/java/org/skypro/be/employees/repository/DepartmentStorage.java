package org.skypro.be.employees.repository;

import jakarta.annotation.PostConstruct;
import org.skypro.be.employees.entity.Department;
import org.skypro.be.employees.exception.DepartmentNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DepartmentStorage implements DepartmentRepository {
    private final static Map<Long, Department> departments = new HashMap<>();
    private long currentId = 0L;


    @PostConstruct
    public void init() {
        List<String> departmentsNames = List.of("Администрация", "Отдел разработки", "Отдел продаж", "Отдел сопровождения");
        for (int i = 0; i < departmentsNames.size(); i++) {
            Department department = new Department();
            department.setId((long) ++i);
            department.setName(departmentsNames.get(i));
            departments.put(department.getId(), department);
        }
    }

    public long getNextId() {
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
    public Department findById(Long id) {
        return departments.entrySet().stream()
                .filter(entry -> entry.getKey().equals(id))
                .findFirst()
                .orElseThrow(() -> new DepartmentNotFoundException("Department with id " + id + " not found"))
                .getValue();
    }

}
