package org.skypro.be.employees.service;

import org.skypro.be.employees.exception.EmployeeAlreadyAddedException;
import org.skypro.be.employees.exception.EmployeeNotFoundException;
import org.skypro.be.employees.exception.EmployeeStorageIsFullException;
import org.skypro.be.employees.repository.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private static final int LIMIT_EMPLOYEES = 10;
    private static final List<Employee> employees = new ArrayList<>(LIMIT_EMPLOYEES);

    public EmployeeServiceImp() {

    }

    public void addEmployee(Employee employee) {
        if (employees.size() >= LIMIT_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Превышен лимит сотрудников в фирме");
        }
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник уже добавлен");
        }
        Employee newemployee = new Employee.Builder(employee.getFirstName(), employee.getLastName())
                .email(employee.getEmail())
                .gender(employee.getGender())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartmentId())
                .build();

        employees.add(newemployee);
    }

    public void deleteEmployee(String firstName, String lastName) {
        Employee employee = findEmployeeByName(firstName, lastName);
        employees.remove(employee);
    }

    public Employee findEmployeeByName(String firstName, String lastName) {
        return employees.stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));
    }

    public Employee getEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
