package org.skypro.be.employees.controller;

import jakarta.validation.Valid;
import org.skypro.be.employees.entity.DepartmentDto;
import org.skypro.be.employees.entity.MapperDepartment;
import org.skypro.be.employees.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public String viewDepartments(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        return "department";
    }

    @GetMapping("/add")
    public String addDepartment(Model model) {
        model.addAttribute("department", new DepartmentDto());
        return "newDepartment";
    }

    @PostMapping("/add")
    public String saveDepartment(@Valid @ModelAttribute("department") DepartmentDto department,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "newDepartment";
        }
        redirectAttributes.addFlashAttribute("newDepartment", departmentService.addDepartment(department));
        return "redirect:/departments";
    }

    @GetMapping("/{id}/edit")
    public String editDepartmentPage(@PathVariable("id") Integer id, Model model) {
        DepartmentDto dto = MapperDepartment.toDto(departmentService.findDepartmentById(id));
        model.addAttribute("department", dto);
        return "editDepartment";
    }

    @PostMapping("/update")
    public String updateDepartment(@Valid @ModelAttribute("department") DepartmentDto department,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "editDepartment";
        }
        redirectAttributes.addFlashAttribute("updatedDepartment", departmentService.updateDepartment(department));
        return "redirect:/departments";
    }

    @GetMapping("/{id}/delete")
    public String deleteDepartment(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("deletedDepartment", departmentService.deleteDepartmentById(id));
        return "redirect:/departments";
    }

    @GetMapping("/{id}/employees")
    public String viewEmployeesOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("employees", departmentService.getEmployeesOfDepartment(id));
        return "employeesOfDepartment";
    }

    @GetMapping("/employees")
    public String viewEmployeesByDepartments(Model model) {
        model.addAttribute("departments", departmentService.getEmployeesByDepartments());
        return "employeesByDepartments";
    }

    @GetMapping("/{id}/min-salary")
    public String viewEmployeeWithMinSalaryOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("minSalary", true);
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("employee", departmentService.getEmployeeWithMinSalaryOfDepartment(id));
        return "employeeData";
    }

    @GetMapping("/{id}/max-salary")
    public String viewEmployeeWithMaxSalaryOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("maxSalary", true);
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("employee", departmentService.getEmployeeWithMaxSalaryOfDepartment(id));
        return "employeeData";
    }

    @GetMapping("{id}/sum")
    public String viewSumSalaryOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("sumSalary", departmentService.getSumSalaryOfDepartment(id));
        return "salary-info";
    }

    @GetMapping("{id}/average")
    public String viewAverageSalaryOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("averageSalary", departmentService.getAverageSalaryOfDepartment(id));
        return "salary-info";
    }

    @GetMapping("{id}/max")
    public String viewMaxSalaryOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("maxSalary", departmentService.getMaxSalaryOfDepartment(id));
        return "salary-info";
    }

    @GetMapping("{id}/min")
    public String viewMinSalaryOfDepartment(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("department", departmentService.findDepartmentById(id));
        model.addAttribute("minSalary", departmentService.getMinSalaryOfDepartment(id));
        return "salary-info";
    }
}
