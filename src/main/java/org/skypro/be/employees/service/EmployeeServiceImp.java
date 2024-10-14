package org.skypro.be.employees.service;

import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.entity.EmployeeDto;
import org.skypro.be.employees.entity.MapperEmployee;
import org.skypro.be.employees.exception.EmployeeAlreadyExistsException;
import org.skypro.be.employees.exception.EmployeeNotFoundException;
import org.skypro.be.employees.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final Repository<Employee> repository;

    public EmployeeServiceImp(Repository<Employee> repository) {
        this.repository = repository;
    }

    @Override
    public Employee addEmployee(EmployeeDto dto) {
        if (repository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName()).isPresent()) {
            throw new EmployeeAlreadyExistsException(dto.getFirstName(), dto.getLastName());
        }
        dto.setId(repository.getNextId());
        return repository.save(MapperEmployee.toEntity(dto));
    }

    @Override
    public Employee deleteEmployeeByName(String firstName, String lastName) {
        Employee employee = findEmployeeByName(firstName, lastName);
        return repository.delete(employee);
    }

    @Override
    public Employee deleteEmployeeById(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return repository.delete(employee);
    }

    public Employee findEmployeeByName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new EmployeeNotFoundException(firstName, lastName));
    }

    public Employee getEmployeeById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Collection<Employee> getEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee updateEmployee(EmployeeDto dto) {
        return repository.save(MapperEmployee.toEntity(dto));
    }

}
