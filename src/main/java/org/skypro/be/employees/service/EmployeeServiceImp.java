package org.skypro.be.employees.service;

import jakarta.annotation.PostConstruct;
import org.skypro.be.employees.exception.EmployeeAlreadyExistsException;
import org.skypro.be.employees.exception.EmployeeNotFoundException;
import org.skypro.be.employees.exception.EmployeeStorageIsFullException;
import org.skypro.be.employees.repository.Employee;
import org.skypro.be.employees.repository.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private static final int LIMIT_EMPLOYEES = 10;
    private static final Map<Long, Employee> employees = new HashMap<>(LIMIT_EMPLOYEES);

    public EmployeeServiceImp() {
    }

    @PostConstruct
    public void init() {
        employees.put(1L, Employee.builder("Сергей", "Васильев").departmentId(1L).build());
        employees.put(2L, Employee.builder("Екатерина", "Воронцова").email("vorontcova@name.org").departmentId(2L).salary(120000).build());
        employees.put(3L, Employee.builder("Владимир", "Иванов").departmentId(2L).salary(110000).build());
        employees.put(4L, Employee.builder("Иван", "Иванов").departmentId(3L).build());
        employees.put(5L, Employee.builder("Ольга", "Волкова").departmentId(4L).build());
    }

    @Override
    public Employee addEmployee(EmployeeDto employee) {
        if (isFull()) {
            throw new EmployeeStorageIsFullException("Превышен лимит сотрудников в фирме");
        }
        if (isExist(employee.getFirstName(), employee.getLastName())) {
            throw new EmployeeAlreadyExistsException(employee.getFirstName(), employee.getLastName());
        }
        Employee newemployee = Employee.builder(employee.getFirstName(), employee.getLastName())
                .email(employee.getEmail())
                .gender(employee.getGender())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartmentId())
                .build();

        employees.put(newemployee.getId(), newemployee);
        return newemployee;
    }

    @Override
    public Employee deleteEmployeeByName(String firstName, String lastName) {
        Employee employee = findEmployeeByName(firstName, lastName);
        return employees.remove(employee.getId());
    }

    @Override
    public Employee deleteEmployee(Long id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException(id);
        }
        return employees.remove(id);
    }

    public Employee findEmployeeByName(String firstName, String lastName) {
        return employees.values().stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(firstName, lastName));
    }

    public Employee getEmployeeById(Long id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException(id);
        }
        return employees.get(id);
    }

    public Collection<Employee> getEmployees() {
        return employees.values();
    }

    @Override
    public Employee updateEmployee(EmployeeDto employee) {
        Employee updatedEmployee = employees.get(employee.getId());
        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setGender(employee.getGender());
        updatedEmployee.setAge(employee.getAge());
        updatedEmployee.setSalary(employee.getSalary());
        updatedEmployee.setDepartmentId(employee.getDepartmentId());
        employees.put(updatedEmployee.getId(), updatedEmployee);
        return updatedEmployee;
    }

    private boolean isExist(String firstName, String lastName) {
        return employees.values().stream()
                .anyMatch(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName));
    }

    private boolean isFull() {
        return employees.size() >= LIMIT_EMPLOYEES;
    }

}
