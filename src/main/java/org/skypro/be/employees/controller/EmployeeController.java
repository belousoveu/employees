package org.skypro.be.employees.controller;

import jakarta.validation.Valid;
import org.skypro.be.employees.entity.Employee;
import org.skypro.be.employees.entity.EmployeeDto;
import org.skypro.be.employees.entity.Gender;
import org.skypro.be.employees.service.DepartmentService;
import org.skypro.be.employees.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
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
        model.addAttribute("employee", new EmployeeDto());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", departmentService.getDepartments());
        return "newEmployee";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeDto dto,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", dto);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("departments", departmentService.getDepartments());
            return "newEmployee";
        }
        if (dto.getId() == 0) {
            Employee employee = employeeService.addEmployee(dto);
            redirectAttributes.addFlashAttribute("newEmployee", employee);
        } else {
            Employee resultEmployee = employeeService.updateEmployee(dto);
            redirectAttributes.addFlashAttribute("updatedEmployee", resultEmployee);
        }
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("department", departmentService.findDepartmentById(employee.getDepartmentId()));
        return "employeeData";
    }

    @GetMapping("/{id}/edit")
    public String editEmployee(@PathVariable Integer id, Model model) {
        model.addAttribute("title", "Редактирование данных сотрудника");
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", departmentService.getDepartments());
        return "newEmployee";
    }

    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Employee deletedEmployee = employeeService.deleteEmployeeById(id);
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
