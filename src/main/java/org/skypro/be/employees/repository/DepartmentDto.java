package org.skypro.be.employees.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.StringUtils;

public class DepartmentDto {
    private Long id;

    @NotBlank(message = "Наименование отдела не может быть пустым")
    @Pattern(regexp = "^[А-Яа-я0-9- ]+$", message = "Наименование отдела содержит недопустимые символы")
    private String name;

    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name) {
        this.id = id;
        this.name = StringUtils.capitalize(name);
    }

    public DepartmentDto(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringUtils.capitalize(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
