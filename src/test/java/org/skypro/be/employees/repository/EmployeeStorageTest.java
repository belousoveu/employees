package org.skypro.be.employees.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.exception.EmployeeStorageIsFullException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeStorageTest {


    private Map<Integer, Employee> employees;

    private final EmployeeStorage out = new EmployeeStorage();

    @BeforeEach
    void setUp() {
        employees = new HashMap<>();
    }

    @Test
    void testSave() {
        Employee newEmployee = getNewEmployee();
        Employee savedEmployee = out.save(newEmployee);

        assertNotNull(savedEmployee);
        assertEquals(newEmployee, savedEmployee);
        assertTrue(out.findById(savedEmployee.getId()).isPresent());
    }

    @Test
    void testSaveWhenStorageIsFull() {
        int limit = out.getLimitEmployees();
        int currentSize = out.findAll().size();
        for (int i = currentSize + 1; i <= limit; i++) {
            Employee newEmployee = Employee.builder(i, "Петр", "Петров" + i).departmentId(3).build();
            out.save(newEmployee);
        }
        Employee updatedEmployee = Employee.builder(1, "Василий", "Васильев").age(50).departmentId(1).salary(150_000).build();
        Employee savedEmployee = out.save(updatedEmployee);
        assertNotNull(savedEmployee);
        assertEquals(updatedEmployee, savedEmployee);
        assertTrue(out.findById(savedEmployee.getId()).isPresent());

        Employee newEmployee = Employee.builder(limit + 1, "Петр", "Петров" + limit + 1).departmentId(3).build();
        assertThrows(EmployeeStorageIsFullException.class, () -> out.save(newEmployee));
    }

    @Test
    void testFindByFirstNameAndLastNameWhenExist() {
        Employee newEmployee = getNewEmployee();
        out.save(newEmployee);
        Optional<Employee> employee = out.findByFirstNameAndLastName("Петр", "Петров");
        assertTrue(employee.isPresent());
    }

    @Test
    void testFindByFirstNameAndLastNameWhenNotExist() {
        Employee newEmployee = getNewEmployee();
        out.save(newEmployee);
        Optional<Employee> employee = out.findByFirstNameAndLastName("Петр", "Иванов");
        assertFalse(employee.isPresent());
    }

    @Test
    void delete() {
        Employee employee = getNewEmployee();
        Employee savedEmployee = out.save(employee);
        Employee deletedEmployee = out.delete(savedEmployee);

        assertNotNull(deletedEmployee);
        assertEquals(employee, deletedEmployee);
        assertFalse(out.findById(deletedEmployee.getId()).isPresent());
    }

    @Test
    void findById() {
        Employee newEmployee = getNewEmployee();
        out.save(newEmployee);
        Optional<Employee> employee = out.findById(1);
        assertTrue(employee.isPresent());
        out.delete(employee.get());
        Optional<Employee> deletedEmployee = out.findById(1);
        assertFalse(deletedEmployee.isPresent());
    }

    @Test
    void findAll() {
        employees.put(1, Employee.builder(1, "Сергей", "Васильев").age(50).departmentId(1).salary(150_000).build());
        employees.put(2, Employee.builder(2, "Екатерина", "Воронцова").email("vorontcova@name.org").departmentId(2).salary(120000).build());
        employees.put(3, Employee.builder(3, "Владимир", "Иванов").departmentId(2).salary(110000).build());
        employees.put(4, Employee.builder(4, "Иван", "Иванов").age(34).departmentId(3).salary(115_000).build());
        employees.put(5, Employee.builder(5, "Ольга", "Волкова").departmentId(4).salary(110_000).build());

        for (int i = 1; i <= 5; i++) {
            out.save(employees.get(i));
        }

        Collection<Employee> employeeCollection = out.findAll();
        Collection<Employee> expectedEmployeeCollection = Collections.unmodifiableCollection(employees.values());
        assertIterableEquals(expectedEmployeeCollection, employeeCollection);
    }

    private Employee getNewEmployee() {
        return Employee.builder(1, "Петр", "Петров").age(34).departmentId(3).salary(115_000).build();
    }
}