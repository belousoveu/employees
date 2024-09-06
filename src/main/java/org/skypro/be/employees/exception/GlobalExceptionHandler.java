package org.skypro.be.employees.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmployeeNotFoundException.class,
            EmployeeAlreadyExistsException.class,
            EmployeeStorageIsFullException.class,
            DepartmentNotFoundException.class,
            UnableDepartmentDeleteException.class})

    public ModelAndView handleEmployeeException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
