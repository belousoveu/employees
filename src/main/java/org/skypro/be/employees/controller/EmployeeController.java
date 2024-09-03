package org.skypro.be.employees.controller;

import org.skypro.be.employees.repository.Employee;
import org.skypro.be.employees.repository.EmployeeDto;
import org.skypro.be.employees.service.DepartmentService;
import org.skypro.be.employees.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String viewEmployees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        return "employee";
    }

    @GetMapping("/add")
    public String addEmployeePage(Model model) {
        model.addAttribute("title", "Добавление нового сотрудника");
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getDepartments());
        return "newemployee";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDto employee, RedirectAttributes redirectAttributes) {
//        Employee resultEmployee;
        if (employee.getId() == null) {
            Employee resultEmployee = employeeService.addEmployee(employee);
            redirectAttributes.addFlashAttribute("newEmployee", resultEmployee);
        } else {
            Employee resultEmployee = employeeService.updateEmployee(employee);
            redirectAttributes.addFlashAttribute("updatedEmployee", resultEmployee);
        }

        return "redirect:/employees";
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getEmployees());
        return "listemployee";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("department", departmentService.getDepartment(employee.getDepartmentId()));
        return "employeedata";
    }

    @GetMapping("/{id}/edit")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Редактирование данных сотрудника");
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getDepartments());
        return "newemployee";
    }

    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Employee deletedEmployee = employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("deletedEmployee", deletedEmployee);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployee(Model model) {
        model.addAttribute("title", "Поиск");
        return "searchEmployee";
    }

    @GetMapping("/delete")
    public String deleteEmployeeByName(Model model) {
        model.addAttribute("title", "Удаление");
        return "searchEmployee";
    }

    @PostMapping("/search")
    public String searchResult(@RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam String action, RedirectAttributes redirectAttributes) {
        if (action.equals("search")) {
            Employee employee = employeeService.findEmployeeByName(firstName, lastName);
            return "redirect:/employees/" + employee.getId();
        } else if (action.equals("delete")) {
            Employee employee = employeeService.deleteEmployeeByName(firstName, lastName);
            redirectAttributes.addFlashAttribute("deletedEmployee", employee);
            return "redirect:/employees";
        }
        return "redirect:/employees";
    }

}
