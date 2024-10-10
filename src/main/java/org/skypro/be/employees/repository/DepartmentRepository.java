package org.skypro.be.employees.repository;

import org.skypro.be.employees.entity.Department;

import java.util.Collection;

public interface DepartmentRepository {

    int getNextId();

    Department save(Department department);

    Department delete(Department department);

    Collection<Department> findAll();

    Department findById(int id);
}
