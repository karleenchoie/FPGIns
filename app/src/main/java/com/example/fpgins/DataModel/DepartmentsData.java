package com.example.fpgins.DataModel;

public class DepartmentsData {
    private String id;
    private String name;
    private String departmentCategoryId;
    private String departmentCategoryName;

    public DepartmentsData(String id, String name, String departmentCategoryId, String departmentCategoryName) {
        this.id = id;
        this.name = name;
        this.departmentCategoryId = departmentCategoryId;
        this.departmentCategoryName = departmentCategoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentCategoryId() {
        return departmentCategoryId;
    }

    public void setDepartmentCategoryId(String departmentCategoryId) {
        this.departmentCategoryId = departmentCategoryId;
    }

    public String getDepartmentCategoryName() {
        return departmentCategoryName;
    }

    public void setDepartmentCategoryName(String departmentCategoryName) {
        this.departmentCategoryName = departmentCategoryName;
    }
}
