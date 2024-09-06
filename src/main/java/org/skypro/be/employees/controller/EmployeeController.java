package org.skypro.be.employees.controller;

import jakarta.validation.Valid;
import org.skypro.be.employees.repository.Employee;
import org.skypro.be.employees.repository.EmployeeDto;
import org.skypro.be.employees.repository.Gender;
import org.skypro.be.employees.service.DepartmentService;
import org.skypro.be.employees.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", departmentService.getDepartments());
        return "newEmployee";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeDto employee,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("departments", departmentService.getDepartments());
            return "newEmployee";
        }
        if (employee.getId() == null) {
            Employee resultEmployee = employeeService.addEmployee(employee);
            redirectAttributes.addFlashAttribute("newEmployee", resultEmployee);
        } else {
            Employee resultEmployee = employeeService.updateEmployee(employee);
            redirectAttributes.addFlashAttribute("updatedEmployee", resultEmployee);
        }
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("department", departmentService.getDepartment(employee.getDepartmentId()));
        return "employeeData";
    }

    @GetMapping("/{id}/edit")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("title", "Редактирование данных сотрудника");
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", departmentService.getDepartments());
        return "newEmployee";
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
