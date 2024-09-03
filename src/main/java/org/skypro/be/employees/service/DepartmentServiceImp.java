package org.skypro.be.employees.service;

import org.skypro.be.employees.exception.DepartmentNotFoundException;
import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.repository.DepartmentDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentServiceImp implements DepartmentService {
    static List<Department> departments = new ArrayList<>();

    static {
        departments.add(new Department("Администрация"));
        departments.add(new Department("Отдел разработки"));
        departments.add(new Department("Отдел продаж"));
        departments.add(new Department("Отдел сопровождения"));
    }

    @Override
    public void addDepartment(String name) {
        departments.add(new Department(name));
    }

    @Override
    public void deleteDepartment(Long id) {
        departments.removeIf(element -> Objects.equals(element.getId(), id));
    }

    @Override
    public void updateDepartment(DepartmentDto department) {
        Long targetId = department.getId();
        String newName = department.getName();
        departments.stream().filter(element -> Objects.equals(element.getId(), targetId))
                .forEach(element -> element.setName(newName));
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public Department getDepartment(Long id) {
        return departments.stream()
                .filter(department -> Objects.equals(department.getId(), id)).findFirst()
                .orElseThrow(() -> new DepartmentNotFoundException("Отдел не найден"));
    }

}
