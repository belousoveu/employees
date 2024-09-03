package org.skypro.be.employees.repository;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Employee {
    private static long currentId = 0;
    Long id;
    String firstName;
    String lastName;
    String email;
    String gender;
    int age;
    int salary;
    Long departmentId;

    public Employee() {
    }

    private Employee(String firstName, String lastName, String email, String gender, int age, int salary, Long departmentId) {
        this.id = ++currentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
        this.departmentId = departmentId;
    }

//    public Employee (EmployeeDto employee) {
//        this.id = ++currentId;
//        this.firstName = employee.getFirstName();
//        this.lastName = employee.getLastName();
//        this.email = employee.getEmail();
//        this.gender = employee.getGender();
//        this.age = employee.getAge();
//        this.salary = employee.getSalary();
//        this.departmentId = employee.getDepartmentId();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return this.getFullName();
    }

    public Long getId() {
        return id;
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

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private String email;
        private String gender;
        private int age;
        private int salary;
        private Long departmentId;

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder salary(int salary) {
            this.salary = salary;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Employee build() {
            return new Employee(firstName, lastName, email, gender, age, salary, departmentId);
        }

    }

}
