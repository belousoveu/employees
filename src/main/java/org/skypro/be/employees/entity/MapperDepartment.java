package org.skypro.be.employees.entity;

public class MapperDepartment {

    public static Department toEntity(DepartmentDto dto) {
        Department entity = new Department();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static DepartmentDto toDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }
}
