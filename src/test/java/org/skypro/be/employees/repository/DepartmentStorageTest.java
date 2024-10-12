package org.skypro.be.employees.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.be.employees.entity.Department;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentStorageTest {

    private Map<Integer, Department> departments;

    private final DepartmentStorage out = new DepartmentStorage();

    @BeforeEach
    void setUp() {
        departments = new HashMap<>();
        Department department = new Department();
        department.setId(1);
        department.setName("Department 1");
        departments.put(department.getId(), department);
    }

    @Test
    void save() {
        Department department = new Department();
        department.setId(2);
        department.setName("Department 2");
        Department savedDepartment = out.save(department);

        assertNotNull(savedDepartment);
        assertEquals(savedDepartment, department);
        assertTrue(out.findById(2).isPresent());

    }

    @Test
    void delete() {
        Department department = new Department();
        department.setId(2);
        department.setName("Department 2");
        Department savedDepartment = out.save(department);
        Department deletedDepartment = out.delete(savedDepartment);

        assertNotNull(deletedDepartment);
        assertEquals(savedDepartment, deletedDepartment);
        assertFalse(out.findById(2).isPresent());
    }

    @Test
    void findAll() {
        out.save(departments.get(1));
        Department department2 = new Department();
        department2.setId(2);
        department2.setName("Department 2");
        out.save(department2);
        departments.put(department2.getId(), department2);

        Collection<Department> actual = out.findAll();
        Collection<Department> expected = Collections.unmodifiableCollection(departments.values());
        assertIterableEquals(expected, actual);

    }

    @Test
    void findById() {
        out.save(departments.get(1));
        Optional<Department> department1 = out.findById(1);
        assertTrue(department1.isPresent());
        Optional<Department> department3 = out.findById(3);
        assertFalse(department3.isPresent());
    }

    @Test
    void findByFirstNameAndLastName() {
    }
}