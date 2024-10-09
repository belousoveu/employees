package org.skypro.be.employees.service;

import org.skypro.be.employees.entity.Department;
import org.skypro.be.employees.entity.DepartmentDto;
import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.entity.MapperDepartment;
import org.skypro.be.employees.exception.DepartmentNotFoundException;
import org.skypro.be.employees.exception.UnableDepartmentDeleteException;
import org.skypro.be.employees.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class DepartmentServiceImp implements DepartmentService {
    private final EmployeeService employeeService;
    private final DepartmentRepository repository;

    public DepartmentServiceImp(EmployeeService employeeService, DepartmentRepository repository) {
        this.employeeService = employeeService;
        this.repository = repository;
    }


    @Override
    public Department addDepartment(DepartmentDto dto) {
        dto.setId(repository.getNextId());
        return repository.save(MapperDepartment.toEntity(dto));
    }

    @Override
    public Department deleteDepartmentById(Long id) {
        if (!isPossibleDelete(id)) {
            throw new UnableDepartmentDeleteException("Unable to delete department with id: " + id + " because it has employees");
        }

        return repository.delete(getDepartmentById(id));
    }

    @Override
    public Department updateDepartment(DepartmentDto dto) {
        return repository.save(MapperDepartment.toEntity(dto));
    }

    @Override
    public Collection<Department> getDepartments() {
        return repository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Employee> getEmployeesOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .toList();
    }

    @Override
    public Map<String, List<Employee>> getEmployeesByDepartments() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(Department::getName, department -> getEmployeesOfDepartment(department.getId())));
    }

    @Override
    public Employee getEmployeeWithMinSalaryOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .min(comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentNotFoundException("Department with id " + id + " not found"));
    }

    @Override
    public Employee getEmployeeWithMaxSalaryOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .max(comparing(Employee::getSalary))
                .orElseThrow(() -> new DepartmentNotFoundException("Department with id " + id + " not found"));
    }

    private boolean isPossibleDelete(Long id) {
        return employeeService.getEmployees().stream()
                .noneMatch(employee -> Objects.equals(employee.getDepartmentId(), id));
    }

}
