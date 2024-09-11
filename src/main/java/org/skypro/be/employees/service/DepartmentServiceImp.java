package org.skypro.be.employees.service;

import jakarta.annotation.PostConstruct;
import org.skypro.be.employees.exception.DepartmentNotFoundException;
import org.skypro.be.employees.exception.UnableDepartmentDeleteException;
import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.repository.DepartmentDto;
import org.skypro.be.employees.repository.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImp implements DepartmentService {
    static Map<Long, Department> departments = new HashMap<>();
    private final EmployeeService employeeService;

    public DepartmentServiceImp(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostConstruct
    public void init() {
        departments.put(1L, new Department("Администрация"));
        departments.put(2L, new Department("Отдел разработки"));
        departments.put(3L, new Department("Отдел продаж"));
        departments.put(4L, new Department("Отдел сопровождения"));
    }

    @Override
    public Department addDepartment(DepartmentDto department) {
        Department newDepartment = new Department(department.getName());
        departments.put(newDepartment.getId(), newDepartment);
        return newDepartment;
    }

    @Override
    public Department deleteDepartment(Long id) {
        if (validateDelete(id)) {
            Department deletedDepartment = getDepartment(id);
            departments.remove(id);
            return deletedDepartment;
        }
        throw new UnableDepartmentDeleteException("В отделе есть сотрудники. Невозможно удалить отдел");
    }

    private boolean validateDelete(Long id) {
        return employeeService.getEmployees().stream()
                .noneMatch(employee -> Objects.equals(employee.getDepartmentId(), id));
    }

    @Override
    public Department updateDepartment(DepartmentDto department) {
        Long targetId = department.getId();
        departments.get(targetId).setName(department.getName());
        return getDepartment(targetId);
    }

    @Override
    public List<Department> getDepartments() {
        return departments.values().stream().toList();
    }

    @Override
    public Department getDepartment(Long id) {
        return departments.get(id);
    }

    @Override
    public List<Employee> getEmployeesOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .toList();
    }

    @Override
    public Map<String, List<Employee>> getEmployeesByDepartments() {
        return departments.values().stream()
                .collect(Collectors.toMap(Department::getName, department -> getEmployeesOfDepartment(department.getId())));
    }

    @Override
    public Employee getEmployeeWithMinSalaryOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .min(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentNotFoundException("Отдел не найден"));
    }

    @Override
    public Employee getEmployeeWithMaxSalaryOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentNotFoundException("Отдел не найден"));
    }

}
