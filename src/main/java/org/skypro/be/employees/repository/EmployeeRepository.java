package org.skypro.be.employees.repository;

import org.skypro.be.employees.entity.Employee;

import java.util.Collection;
import java.util.Optional;

public interface EmployeeRepository {


    Long getNextId();

    Employee save(Employee employee);

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Employee delete(Employee employee);

    Optional<Employee> findById(Long id);

    Collection<Employee> findAll();
}
