package org.skypro.be.employees.entity;

public class MapperEmployee {

    public static Employee toEntity(EmployeeDto dto) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setSalary(dto.getSalary());
        entity.setDepartmentId(dto.getDepartmentId());
        return entity;
    }

    public static EmployeeDto toDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setSalary(entity.getSalary());
        dto.setDepartmentId(entity.getDepartmentId());
        return dto;
    }
}
