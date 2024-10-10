package org.skypro.be.employees.repository;

import jakarta.annotation.PostConstruct;
import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.exception.EmployeeStorageIsFullException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmployeeStorage implements EmployeeRepository {
    private static int currentId = 0;
    private static final int LIMIT_EMPLOYEES = 10;
    private static final Map<Integer, Employee> employees = new HashMap<>(LIMIT_EMPLOYEES);


    @PostConstruct
    public void init() {
        employees.put(++currentId, Employee.builder(currentId, "Сергей", "Васильев").age(50).departmentId(1).salary(150_000).build());
        employees.put(++currentId, Employee.builder(currentId, "Екатерина", "Воронцова").email("vorontcova@name.org").departmentId(2).salary(120000).build());
        employees.put(++currentId, Employee.builder(currentId, "Владимир", "Иванов").departmentId(2).salary(110000).build());
        employees.put(++currentId, Employee.builder(currentId, "Иван", "Иванов").age(34).departmentId(3).salary(115_000).build());
        employees.put(++currentId, Employee.builder(currentId, "Ольга", "Волкова").departmentId(4).salary(110_000).build());
    }

    @Override
    public int getNextId() {
        return ++currentId;
    }

    @Override
    public Employee save(Employee employee) {
        if (isFull() && !employees.containsKey(employee.getId())) {
            throw new EmployeeStorageIsFullException("Employee storage is full");
        }
        return employees.put(employee.getId(), employee);
    }

    @Override
    public Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employees.values().stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public Employee delete(Employee employee) {
        return employees.remove(employee.getId());
    }

    @Override
    public Optional<Employee> findById(int id) {
        return Optional.of(employees.getOrDefault(id, null));
    }

    @Override
    public Collection<Employee> findAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private boolean isFull() {
        return employees.size() >= LIMIT_EMPLOYEES;
    }
}
