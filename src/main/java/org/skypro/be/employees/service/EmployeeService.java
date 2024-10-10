package org.skypro.be.employees.service;

import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.entity.EmployeeDto;

import java.util.Collection;

public interface EmployeeService {

    Employee addEmployee(EmployeeDto employee);

    Employee deleteEmployeeByName(String firstName, String lastName);

    Employee findEmployeeByName(String firstName, String lastName);

    Employee getEmployeeById(int id);

    Collection<Employee> getEmployees();

    Employee updateEmployee(EmployeeDto employee);

    Employee deleteEmployeeById(int id);
}
