package org.skypro.be.employees.service;

import org.skypro.be.employees.repository.Employee;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee);

    void deleteEmployee(String firstName, String lastName);

    Employee findEmployeeByName(String firstName, String lastName);

    Employee getEmployeeById(Long id);

    List<Employee> getEmployees();


}
