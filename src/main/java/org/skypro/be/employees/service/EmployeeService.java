package org.skypro.be.employees.service;

import org.skypro.be.employees.repository.Employee;

import java.util.Set;

public interface EmployeeService {

    void addEmployee(String firstName, String lastName);

    void deleteEmployee(String firstName, String lastName);

    Employee findEmployee(String firstName, String lastName);

    Set<Employee> getEmployees();
}
