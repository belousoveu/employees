package org.skypro.be.employees.controller;

import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.repository.Employee;
import org.skypro.be.employees.service.DepartmentService;
import org.skypro.be.employees.service.DepartmentServiceImp;
import org.skypro.be.employees.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController extends MainController {
    EmployeeService employeeService;
    DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }


    @GetMapping("")
    public String viewEmployeesPage(Model model) {
        return "employee";
    }

    @GetMapping("/add")
    public String addEmployeePage(Model model) {
        model.addAttribute("employee", new Employee());
        List<Department> departments=departmentService.getDepartments();
        System.out.println(departments.size());
        departments.forEach(System.out::println);
        model.addAttribute("departments", departmentService.getDepartments() );
        return "newemployee";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        System.out.println(employee);
        System.out.println(employee.getFirstName());
        System.out.println(employee.getLastName());
        System.out.println(employee.getEmail());
        System.out.println(employee.getGender());
        System.out.println(employee.getDepartmentId());
        if (employee.getId() == null) {
            employeeService.addEmployee(employee);
        }
        return "redirect:/";
    }

    @GetMapping("/list")
    public String listEmployeesPage(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        return "listemployee";
    }

    @GetMapping("/{id}")
    public String viewEmployeePage(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("department", departmentService.getDepartment(employee.getDepartmentId()));
        return "employeedata";
    }

}
