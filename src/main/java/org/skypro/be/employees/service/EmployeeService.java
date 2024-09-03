package org.skypro.be.employees.service;

import org.skypro.be.employees.repository.Employee;
import org.skypro.be.employees.repository.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(EmployeeDto employee);

    Employee deleteEmployeeByName(String firstName, String lastName);

    Employee findEmployeeByName(String firstName, String lastName);

    Employee getEmployeeById(Long id);

    List<Employee> getEmployees();


    Employee updateEmployee(EmployeeDto employee);

    Employee deleteEmployee(Long id);
}
