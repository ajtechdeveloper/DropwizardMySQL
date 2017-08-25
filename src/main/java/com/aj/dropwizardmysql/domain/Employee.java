package com.aj.dropwizardmysql.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String department;
    @JsonProperty
    private Integer salary;

    public Employee() {
        super();
    }

    public Employee(String name, String department, Integer salary) {
        super();
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
