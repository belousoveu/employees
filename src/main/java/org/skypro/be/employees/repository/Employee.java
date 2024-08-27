package org.skypro.be.employees.repository;

public class Employee {
    private static long currentId = 0;
    long id;
    String firstName;
    String lastName;
    String email;
    String sex;
    int age;
    int salary;
    int departmentId;

    private Employee(String firstName, String lastName, String email, String sex, int age, int salary, int departmentId) {
        this.id = ++currentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.salary = salary;
        this.departmentId = departmentId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public static class Builder {
        private final String firstName;
        private final String lastName;
        private String email;
        private String sex;
        private int age;
        private int salary;
        private int departmentId;

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder sex(String sex) {
            this.sex = sex;
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

        public Builder departmentId(int departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Employee build() {
            return new Employee(firstName, lastName, email, sex, age, salary, departmentId);
        }

    }

}
