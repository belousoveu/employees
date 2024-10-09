package org.skypro.be.employees.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private int age;
    private int salary;
    private Long departmentId;

    public Employee() {
    }

    private Employee(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.gender = builder.gender;
        this.age = builder.age;
        this.salary = builder.salary;
        this.departmentId = builder.departmentId;
    }

    public static Builder builder(Long id, String firstName, String lastName) {
        return new Builder(id, firstName, lastName);
    }

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


    public String getFullName() {
        return lastName + " " + firstName;
    }

    public static class Builder {
        private final Long id;
        private final String firstName;
        private final String lastName;
        private String email;
        private Gender gender;
        private int age;
        private int salary;
        private Long departmentId;

        public Builder(Long id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder gender(Gender gender) {
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
            return new Employee(this);
        }

    }

}
