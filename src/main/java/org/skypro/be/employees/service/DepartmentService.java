package org.skypro.be.employees.service;

import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.repository.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    void addDepartment(String name);

    void deleteDepartment(Long id);

    void updateDepartment(DepartmentDto department);

    List<Department> getDepartments();

    Department getDepartment(Long id);

//    static List<Department> getAllDepartments() {
//        return new DepartmentServiceImp().getDepartments();
//    }
}
