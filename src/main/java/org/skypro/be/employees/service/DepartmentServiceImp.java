package org.skypro.be.employees.service;

import org.skypro.be.employees.exception.DepartmentNotFoundException;
import org.skypro.be.employees.exception.UnableDepartmentDeleteException;
import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.repository.DepartmentDto;
import org.skypro.be.employees.repository.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentServiceImp implements DepartmentService {
    static List<Department> departments = new ArrayList<>();
    private final EmployeeService employeeService;

    static {
        departments.add(new Department("Администрация"));
        departments.add(new Department("Отдел разработки"));
        departments.add(new Department("Отдел продаж"));
        departments.add(new Department("Отдел сопровождения"));
    }

    public DepartmentServiceImp(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Department addDepartment(DepartmentDto department) {
        Department newDepartment=new Department(department.getName());
        departments.add(newDepartment);
        return newDepartment;
    }

    @Override
    public Department deleteDepartment(Long id) {
        if (validateDelete(id)) {
            Department deletedDepartment=getDepartment(id);
            departments.remove(deletedDepartment);
            return deletedDepartment;
        } else {
            throw new UnableDepartmentDeleteException("В отделе есть сотрудники. Невозможно удалить отдел");
        }
    }

    private boolean validateDelete(Long id) {
        return employeeService.getEmployees().stream()
                .noneMatch(employee -> Objects.equals(employee.getDepartmentId(), id));
    }

    @Override
    public Department updateDepartment(DepartmentDto department) {
        Long targetId = department.getId();
        String newName = department.getName();
        departments.stream().filter(element -> Objects.equals(element.getId(), targetId))
                .forEach(element -> element.setName(newName));
        return getDepartment(targetId);
    }

    @Override
    public List<Department> getDepartments() {
        return departments;
    }

    @Override
    public Department getDepartment(Long id) {
        return departments.stream()
                .filter(department -> Objects.equals(department.getId(), id)).findFirst()
                .orElseThrow(() -> new DepartmentNotFoundException("Отдел не найден"));
    }

    @Override
    public List<Employee> getEmployeesOfDepartment(Long id) {
        return employeeService.getEmployees().stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), id))
                .toList();
    }

    @Override
    public Map<String, List<Employee>> getEmployeesByDepartments() {
        Map<String, List<Employee>> result = new HashMap<>();
        for (Department department : departments) {
            result.put(department.getName(), getEmployeesOfDepartment(department.getId()));
        }
        return result;
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

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
}
