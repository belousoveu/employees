package org.skypro.be.employees.service;

import org.skypro.be.employees.entity.Department;
import org.skypro.be.employees.entity.DepartmentDto;
import org.skypro.be.employees.entity.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department addDepartment(DepartmentDto department);

    Department deleteDepartmentById(int id);

    Department updateDepartment(DepartmentDto department);

    Collection<Department> getDepartments();

    Department findDepartmentById(int id);

    List<Employee> getEmployeesOfDepartment(int id);

    Map<String, List<Employee>> getEmployeesByDepartments();

    Employee getEmployeeWithMinSalaryOfDepartment(int id);

    Employee getEmployeeWithMaxSalaryOfDepartment(int id);

    int getSumSalaryOfDepartment(int id);

    double getAverageSalaryOfDepartment(int id);

    int getMaxSalaryOfDepartment(int id);

    int getMinSalaryOfDepartment(int id);
}
