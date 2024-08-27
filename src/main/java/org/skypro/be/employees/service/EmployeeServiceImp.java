package org.skypro.be.employees.service;

import org.skypro.be.employees.exception.EmployeeAlreadyAddedException;
import org.skypro.be.employees.exception.EmployeeNotFoundException;
import org.skypro.be.employees.exception.EmployeeStorageIsFullException;
import org.skypro.be.employees.repository.Employee;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private static final int LIMIT_EMPLOYEES = 10;
    private static final Set<Employee> employees = new HashSet<>(LIMIT_EMPLOYEES);

    public EmployeeServiceImp() {

    }

    public void addEmployee(String firstName, String lastName) {
        if (employees.size() >= LIMIT_EMPLOYEES) {
            throw new EmployeeStorageIsFullException("Превышен лимит сотрудников в фирме");
        }
        Employee employee = new Employee.Builder(firstName, lastName).build();
        // В данном случае проверка на наличие сотрудника не является необходимым, т.к. в любом случае в коллекции
        // HashSet не может быть дубликатов. Проверка сделана исключительно для соблюдения условия задания по выбросу
        // исключения EmployeeAlreadyAddedException.
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник уже добавлен");
        }
        employees.add(employee);
    }

    public void deleteEmployee(String firstName, String lastName) {
        Employee employee = findEmployee(firstName, lastName);
        employees.remove(employee);
    }

    public Employee findEmployee(String firstName, String lastName) {
        return employees.stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник не найден"));
    }

    public Set<Employee> getEmployees() {
        return employees;
    }
}
