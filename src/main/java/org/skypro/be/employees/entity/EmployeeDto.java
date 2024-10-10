package org.skypro.be.employees.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;


@Getter
public class EmployeeDto {
    @Setter
    int id;

    @NotBlank(message = "Поле 'Имя' не может быть пустым")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле 'Имя' может состоять только из букв")
    @Size(min = 2, max = 50, message = "Поле 'Имя' должно содержать от 2 до 50 символов")
    String firstName;

    @NotBlank(message = "Поле 'Фамилия' не может быть пустым")
    @Pattern(regexp = "^[а-яА-Я- ]+$", message = "Поле 'Фамилия' может состоять только из букв")
    @Size(min = 2, max = 50, message = "Поле 'Фамилия' должно содержать от 2 до 50 символов")
    String lastName;

    @Setter
    @Pattern(regexp = "^$|^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Поле 'Email' не соответствует шаблону")
    String email;

    @Setter
    Gender gender;

    @Setter
    @PositiveOrZero(message = "Поле 'Возраст' должно содержать положительное число")
    int age;

    @Setter
    @PositiveOrZero(message = "Поле 'Заработная плата' должно содержать положительное число")
    int salary;

    @Setter
    @NotNull(message = "Поле 'Отдел' не может быть пустым")
    @Min(value = 1, message = "Поле 'Отдел' не может быть пустым")
    int departmentId;

    public EmployeeDto() {
    }

    public void setFirstName(String firstName) {
        this.firstName = StringUtils.capitalize(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = StringUtils.capitalize(lastName);
    }

}
