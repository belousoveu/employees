package org.skypro.be.employees.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.be.employees.entity.Department;
import org.skypro.be.employees.entity.DepartmentDto;
import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.entity.MapperDepartment;
import org.skypro.be.employees.exception.DepartmentNotFoundException;
import org.skypro.be.employees.exception.UnableDepartmentDeleteException;
import org.skypro.be.employees.repository.Repository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImpTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Repository<Department> repository;

    @InjectMocks
    private DepartmentServiceImp out;

    private Map<Integer, Department> departments;
    private Map<Integer, Employee> employees;

    @BeforeEach
    void setUp() {
        int currentId = 0;
        departments = new HashMap<>();
        employees = new HashMap<>();
//        out = new DepartmentServiceImp(employeeService, repository);
        List<String> departmentsNames = List.of("Администрация", "Отдел разработки", "Отдел продаж", "Отдел сопровождения");
        for (String name : departmentsNames) {
            Department department = new Department();
            department.setId(++currentId);
            department.setName(name);
            departments.put(department.getId(), department);
        }
        currentId = 0;
        employees.put(++currentId, Employee.builder(currentId, "Сергей", "Васильев").age(50).departmentId(1).salary(150_000).build());
        employees.put(++currentId, Employee.builder(currentId, "Екатерина", "Воронцова").email("vorontcova@name.org").departmentId(2).salary(120000).build());
        employees.put(++currentId, Employee.builder(currentId, "Владимир", "Иванов").departmentId(2).salary(110000).build());
        employees.put(++currentId, Employee.builder(currentId, "Иван", "Иванов").age(34).departmentId(3).salary(115_000).build());
        employees.put(++currentId, Employee.builder(currentId, "Ольга", "Волкова").departmentId(3).salary(110_000).build());


    }

    @Test
    void testAddDepartment() {
        DepartmentDto expectedDto = new DepartmentDto();
        expectedDto.setId(5);
        expectedDto.setName("department test");
        Department expected = MapperDepartment.toEntity(expectedDto);

        when(repository.getNextId()).thenReturn(5);
        when(repository.save(expected)).thenReturn(expected);
        Department actual = out.addDepartment(expectedDto);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(actual.getId(), 5);
    }

    @Test
    void testUpdateDepartment() {
        DepartmentDto expectedDto = new DepartmentDto();
        expectedDto.setId(1);
        expectedDto.setName("department test");
        Department expected = MapperDepartment.toEntity(expectedDto);

        when(repository.save(expected)).thenReturn(expected);
        Department actual = out.updateDepartment(expectedDto);
        assertNotNull(actual);
        assertEquals(expected, actual);
        assertEquals(actual.getId(), 1);
    }

    @Test
    void testDeleteDepartmentByIdWhenDepartmentIsEmpty() {
        Department expected = departments.get(4);
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));
        when(repository.findById(4)).thenReturn(Optional.of(expected));
        when(repository.delete(expected)).thenReturn(expected);

        Department actual = out.deleteDepartmentById(4);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteDepartmentByIdWhenDepartmentHasEmployees() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        assertThrows(UnableDepartmentDeleteException.class, () -> out.deleteDepartmentById(1));
    }

    @Test
    void testDeleteDepartmentByIdWhenDepartmentNotFound() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));
        when(repository.findById(5)).thenThrow(DepartmentNotFoundException.class);

        assertThrows(DepartmentNotFoundException.class, () -> out.deleteDepartmentById(5));
    }

    @Test
    void testGetDepartments() {
        Collection<Department> expected = Collections.unmodifiableCollection(departments.values());
        when(repository.findAll()).thenReturn(expected);
        Collection<Department> actual = out.getDepartments();
        assertNotNull(actual);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testFindDepartmentById() {
        Department expected = departments.get(1);
        when(repository.findById(expected.getId())).thenReturn(Optional.of(expected));
        Department actual = out.findDepartmentById(1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testFindDepartmentByIdWhenNotFound() {
        when(repository.findById(5)).thenThrow(DepartmentNotFoundException.class);

        assertThrows(DepartmentNotFoundException.class, () -> out.findDepartmentById(5));
    }

    @Test
    void testGetEmployeesOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));
        List<Employee> expected = new ArrayList<>(List.of(employees.get(2), employees.get(3)));
        List<Employee> actual = out.getEmployeesOfDepartment(2);

        assertNotNull(actual);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testGetEmployeesOfDepartmentWhenDepartmentNotExists() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));
        List<Employee> expected = new ArrayList<>();
        List<Employee> actual = out.getEmployeesOfDepartment(5);

        assertNotNull(actual);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testGetEmployeesByDepartments() {
        Map<String, List<Employee>> expected = new HashMap<>();
        expected.put("Администрация", List.of(employees.get(1)));
        expected.put("Отдел разработки", List.of(employees.get(2), employees.get(3)));
        expected.put("Отдел продаж", List.of(employees.get(4), employees.get(5)));
        expected.put("Отдел сопровождения", List.of());

        when(repository.findAll()).thenReturn(Collections.unmodifiableCollection(departments.values()));
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        Map<String, List<Employee>> actual = out.getEmployeesByDepartments();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testGetEmployeeWithMinSalaryOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        Employee actual = out.getEmployeeWithMinSalaryOfDepartment(2);
        Employee expected = employees.get(3);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testGetEmployeeWithMinSalaryOfDepartmentWhenDepartmentNotFound() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        assertThrows(DepartmentNotFoundException.class, () -> out.getEmployeeWithMinSalaryOfDepartment(5));
    }

    @Test
    void testGetEmployeeWithMaxSalaryOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        Employee actual = out.getEmployeeWithMaxSalaryOfDepartment(2);
        Employee expected = employees.get(2);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testGetEmployeeWithMaxSalaryOfDepartmentWhenDepartmentNotFound() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        assertThrows(DepartmentNotFoundException.class, () -> out.getEmployeeWithMaxSalaryOfDepartment(5));
    }

    @Test
    void testGetSumSalaryOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        int expected = 230_000;
        int actual = out.getSumSalaryOfDepartment(2);

        assertEquals(expected, actual);
    }

    @Test
    void getAverageSalaryOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        double expected = 115_000.0;
        double actual = out.getAverageSalaryOfDepartment(2);

        assertEquals(expected, actual);
    }

    @Test
    void getMaxSalaryOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        double expected = 120_000.0;
        double actual = out.getMaxSalaryOfDepartment(2);

        assertEquals(expected, actual);
    }

    @Test
    void getMinSalaryOfDepartment() {
        when(employeeService.getEmployees()).thenReturn(Collections.unmodifiableCollection(employees.values()));

        double expected = 110_000.0;
        double actual = out.getMinSalaryOfDepartment(2);

        assertEquals(expected, actual);
    }
}