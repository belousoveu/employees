package org.skypro.be.employees.service;

import org.skypro.be.employees.entity.Department;
import org.skypro.be.employees.entity.DepartmentDto;
import org.skypro.be.employees.entity.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department addDepartment(DepartmentDto department);

    Department deleteDepartmentById(Long id);

    Department updateDepartment(DepartmentDto department);

    Collection<Department> getDepartments();

    Department getDepartmentById(Long id);

    List<Employee> getEmployeesOfDepartment(Long id);

    Map<String, List<Employee>> getEmployeesByDepartments();

    Employee getEmployeeWithMinSalaryOfDepartment(Long id);

    Employee getEmployeeWithMaxSalaryOfDepartment(Long id);
}
