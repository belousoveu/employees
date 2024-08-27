package org.skypro.be.employees.controller;

import org.skypro.be.employees.repository.Department;
import org.skypro.be.employees.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public String viewDepartmentsPage(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        return "department";
    }

    @GetMapping("/add")
    public String addDepartmentPage(Model model) {
        return "newdepartment";
    }

    @PostMapping("/add")
    public String saveDepartment(@RequestParam("name") String name, Model model) {
        departmentService.addDepartment(name);
        model.addAttribute("departments", departmentService.getDepartments());
        return "department";
    }

    @GetMapping("/edit/{id}")
    public String editDepartmentPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartment(id));
        return "editdepartment";
    }

    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute("department") Department department, Model model) {
        departmentService.updateDepartment(department);
        model.addAttribute("departments", departmentService.getDepartments());
        return "department";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id, Model model) {
        departmentService.deleteDepartment(id);
        model.addAttribute("departments", departmentService.getDepartments());
        return "department";
    }
}
