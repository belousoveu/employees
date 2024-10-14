package org.skypro.be.employees.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.entity.EmployeeDto;
import org.skypro.be.employees.entity.MapperEmployee;
import org.skypro.be.employees.exception.EmployeeAlreadyExistsException;
import org.skypro.be.employees.exception.EmployeeNotFoundException;
import org.skypro.be.employees.repository.EmployeeStorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImpTest {

    @Mock
    private EmployeeStorage repository;

    @InjectMocks
    private EmployeeServiceImp out;

    private Map<Integer, Employee> employees;


    @BeforeEach
    void setUp() {
        out = new EmployeeServiceImp(repository);
        employees = new HashMap<>();
        employees.put(1, Employee.builder(1, "Сергей", "Васильев").age(50).departmentId(1).salary(150_000).build());
        employees.put(2, Employee.builder(2, "Екатерина", "Воронцова").email("vorontcova@name.org").departmentId(2).salary(120000).build());
        employees.put(3, Employee.builder(3, "Владимир", "Иванов").departmentId(2).salary(110000).build());
    }

    @Test
    void testGetEmployeeById() {
        when(repository.findById(1)).thenReturn(Optional.of(employees.get(1)));
        Employee expectedEmployee = out.getEmployeeById(1);

        assertNotNull(expectedEmployee);
        assertEquals(employees.get(1), expectedEmployee);
    }

    @Test
    void testGetEmployeeByIdWhenNotFound() {
        when(repository.findById(50)).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeById(50));
    }

    @Test
    void testGetEmployees() {
        when(repository.findAll()).thenReturn(Collections.unmodifiableCollection(employees.values()));
        assertIterableEquals(Collections.unmodifiableCollection(employees.values()), out.getEmployees());
    }

    @Test
    void testFindEmployeeByName() {
        when(repository.findByFirstNameAndLastName("Екатерина", "Воронцова")).thenReturn(Optional.of(employees.get(2)));
        Employee expectedEmployee = out.findEmployeeByName("Екатерина", "Воронцова");
        assertNotNull(expectedEmployee);
        assertEquals(employees.get(2), expectedEmployee);
    }

    @Test
    void testFindEmployeeByNameWhenNotFound() {
        when(repository.findByFirstNameAndLastName("Сергей", "Иванов")).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> out.findEmployeeByName("Сергей", "Иванов"));
    }

    @Test
    void testAddEmployee() {

        EmployeeDto newEmployee = new EmployeeDto();
        newEmployee.setId(4);
        newEmployee.setFirstName("Иван");
        newEmployee.setLastName("Иванов");
        newEmployee.setDepartmentId(2);
        newEmployee.setSalary(120_000);

        when(repository.getNextId()).thenReturn(4);
        when(repository.findByFirstNameAndLastName("Иван", "Иванов")).thenReturn(Optional.empty());
        when(repository.save(MapperEmployee.toEntity(newEmployee))).thenReturn(MapperEmployee.toEntity(newEmployee));

        Employee expectedEmployee = out.addEmployee(newEmployee);
        employees.put(expectedEmployee.getId(), expectedEmployee);
        assertNotNull(expectedEmployee);
        assertEquals(employees.get(4), expectedEmployee);

    }

    @Test
    void testAddEmployeeWhenExisting() {

        EmployeeDto newEmployee = new EmployeeDto();
        newEmployee.setId(0);
        newEmployee.setFirstName("Екатерина");
        newEmployee.setLastName("Воронцова");
        newEmployee.setDepartmentId(3);

        when(repository.findByFirstNameAndLastName("Екатерина", "Воронцова")).thenReturn(Optional.of(employees.get(2)));

        assertThrows(EmployeeAlreadyExistsException.class, () -> out.addEmployee(newEmployee));
    }

    @Test
    void testDeleteEmployeeByName() {
        when(repository.findByFirstNameAndLastName("Екатерина", "Воронцова")).thenReturn(Optional.of(employees.get(2)));
        when(repository.delete(employees.get(2))).thenReturn(employees.get(2));
        Employee expectedEmployee = out.deleteEmployeeByName("Екатерина", "Воронцова");
        assertNotNull(expectedEmployee);
        assertEquals(employees.get(2), expectedEmployee);

    }

    @Test
    void testDeleteEmployeeByNameWhenNotFound() {
        when(repository.findByFirstNameAndLastName("Сергей", "Иванов")).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> out.deleteEmployeeByName("Сергей", "Иванов"));
    }

    @Test
    void testDeleteEmployeeById() {
        when(repository.findById(1)).thenReturn(Optional.of(employees.get(1)));
        when(repository.delete(employees.get(1))).thenReturn(employees.get(1));
        Employee expectedEmployee = out.deleteEmployeeById(1);
        assertNotNull(expectedEmployee);
        assertEquals(employees.get(1), expectedEmployee);
    }

    @Test
    void testDeleteEmployeeByIdWhenNotFound() {
        when(repository.findById(50)).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> out.deleteEmployeeById(50));
    }

    @Test
    void testUpdateEmployee() {
        EmployeeDto newEmployee = new EmployeeDto();
        newEmployee.setId(2);
        newEmployee.setFirstName("Ирина");
        newEmployee.setLastName("Воронцова");
        newEmployee.setDepartmentId(3);
        newEmployee.setSalary(120_000);

        when(repository.save(MapperEmployee.toEntity(newEmployee))).thenReturn(MapperEmployee.toEntity(newEmployee));
        employees.put(newEmployee.getId(), MapperEmployee.toEntity(newEmployee));
        Employee expectedEmployee = out.updateEmployee(newEmployee);
        assertNotNull(expectedEmployee);
        assertEquals(employees.get(2), expectedEmployee);
    }
}