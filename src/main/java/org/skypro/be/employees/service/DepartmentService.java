package org.skypro.be.employees.service;

import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.repository.DepartmentDto;
import org.skypro.be.employees.repository.Employee;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department addDepartment(DepartmentDto department);

    Department deleteDepartment(Long id);

    Department updateDepartment(DepartmentDto department);

    List<Department> getDepartments();

    Department getDepartment(Long id);

    List<Employee> getEmployeesOfDepartment(Long id);

    Map<String, List<Employee>> getEmployeesByDepartments();

    Employee getEmployeeWithMinSalaryOfDepartment(Long id);

    Employee getEmployeeWithMaxSalaryOfDepartment(Long id);
}
