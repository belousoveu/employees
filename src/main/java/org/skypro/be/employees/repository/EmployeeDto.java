package org.skypro.be.employees.repository;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDto {
    Long id;

    @NotBlank(message = "Поле 'Имя' не может быть пустым")
    @Pattern(regexp = "^[а-яА-Я]+$", message = "Поле 'Имя' может состоять только из букв")
    @Size(min = 2, max = 50, message = "Поле 'Имя' должно содержать от 2 до 50 символов")
    String firstName;

    @NotBlank(message = "Поле 'Фамилия' не может быть пустым")
    @Pattern(regexp = "^[а-яА-Я- ]+$", message = "Поле 'Фамилия' может состоять только из букв")
    @Size(min = 2, max = 50, message = "Поле 'Фамилия' должно содержать от 2 до 50 символов")
    String lastName;

    @Pattern(regexp = "^$|^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "Поле 'Email' не соответствует шаблону")
    String email;

    String gender;
    @PositiveOrZero(message = "Поле 'Возраст' должно содержать положительное число")
    int age;

    @PositiveOrZero(message = "Поле 'Заработная плата' должно содержать положительное число")
    int salary;

    @NotNull(message = "Поле 'Отдел' не может быть пустым")
    @Min(value = 1, message = "Поле 'Отдел' не может быть пустым")
    Long departmentId;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.email = employee.getEmail();
        this.gender = employee.getGender();
        this.age = employee.getAge();
        this.salary = employee.getSalary();
        this.departmentId = employee.getDepartmentId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
