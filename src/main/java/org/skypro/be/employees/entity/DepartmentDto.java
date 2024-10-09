package org.skypro.be.employees.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class DepartmentDto {
    @Setter
    private Long id;

    @NotBlank(message = "Наименование отдела не может быть пустым")
    @Pattern(regexp = "^[А-Яа-я0-9- ]+$", message = "Наименование отдела содержит недопустимые символы")
    private String name;

    public DepartmentDto() {
    }

    public void setName(String name) {
        this.name = StringUtils.capitalize(name);
    }

}
