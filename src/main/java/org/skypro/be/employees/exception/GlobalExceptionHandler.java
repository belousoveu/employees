package org.skypro.be.employees.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmployeeNotFoundException.class,
            EmployeeAlreadyExistsException.class,
            EmployeeStorageIsFullException.class,
            DepartmentNotFoundException.class,
            UnableDepartmentDeleteException.class})
    public ResponseEntity<String> handleEmployeeException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
