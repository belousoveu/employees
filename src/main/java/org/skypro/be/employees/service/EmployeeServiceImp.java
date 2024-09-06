package org.skypro.be.employees.service;

import org.skypro.be.employees.exception.EmployeeAlreadyExistsException;
import org.skypro.be.employees.exception.EmployeeNotFoundException;
import org.skypro.be.employees.exception.EmployeeStorageIsFullException;
import org.skypro.be.employees.repository.Employee;
import org.skypro.be.employees.repository.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private static final int LIMIT_EMPLOYEES = 10;
    private static final Map<Long, Employee> employees = new HashMap<>(LIMIT_EMPLOYEES);

    static {
        employees.put(1L, new Employee.Builder("Сергей", "Васильев").departmentId(1L).build());
        employees.put(2L, new Employee.Builder("Екатерина", "Воронцова").email("vorontcova@name.org").departmentId(2L).salary(120000).build());
        employees.put(3L, new Employee.Builder("Владимир", "Иванов").departmentId(2L).build());
        employees.put(4L, new Employee.Builder("Иван", "Иванов").departmentId(3L).build());
        employees.put(5L, new Employee.Builder("Ольга", "Волкова").departmentId(4L).build());
    }

    public EmployeeServiceImp() {

    }

    public Employee addEmployee(EmployeeDto employee) {
        if (isFull()) {
            throw new EmployeeStorageIsFullException("Превышен лимит сотрудников в фирме");
        }
        if (isExist(employee.getFirstName(), employee.getLastName())) {
            throw new EmployeeAlreadyExistsException("Сотрудник "+employee.getLastName()+" "+employee.getFirstName()+" уже добавлен в базу");
        }
        Employee newemployee = new Employee.Builder(employee.getFirstName(), employee.getLastName())
                .email(employee.getEmail())
                .gender(employee.getGender())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartmentId())
                .build();

        employees.put(newemployee.getId(), newemployee);
        return newemployee;
    }

    public Employee deleteEmployeeByName(String firstName, String lastName) {
        Employee employee = findEmployeeByName(firstName, lastName);
        return employees.remove(employee.getId());
    }

    public Employee deleteEmployee(Long id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException("Сотрудник c id=" + id + " не найден");
        }
        return employees.remove(id);
    }

    public Employee findEmployeeByName(String firstName, String lastName) {
        return employees.values().stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Сотрудник " + firstName + " " + lastName + " не найден"));
    }

    public Employee getEmployeeById(Long id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException("Сотрудник c id=" + id + " не найден");
        }
        return employees.get(id);
    }

    public List<Employee> getEmployees() {
        return employees.values().stream().toList();
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
