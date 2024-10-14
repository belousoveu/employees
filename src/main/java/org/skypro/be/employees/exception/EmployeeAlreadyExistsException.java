package org.skypro.be.employees.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {

    public EmployeeAlreadyExistsException(String firstName, String lastName) {
        super("Employee" + firstName + " " + lastName + " already exists");
    }
}
