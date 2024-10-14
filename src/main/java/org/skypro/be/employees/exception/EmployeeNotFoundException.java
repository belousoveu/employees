package org.skypro.be.employees.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String firstName, String lastName) {
        super("Сотрудник " + firstName + " " + lastName + " не найден");
    }

    public EmployeeNotFoundException(int id) {
        super("Сотрудник с id " + id + " не найден");
    }

}
