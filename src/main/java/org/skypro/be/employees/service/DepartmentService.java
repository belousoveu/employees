package org.skypro.be.employees.service;

import org.skypro.be.employees.repository.Department;

import java.util.List;

public interface DepartmentService {
    void addDepartment(String name);

    void deleteDepartment(Long id);

    void updateDepartment(Department department);

    List<Department> getDepartments();

    Department getDepartment(Long id);
}
