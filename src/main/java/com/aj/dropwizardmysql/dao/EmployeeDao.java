package com.aj.dropwizardmysql.dao;

import com.aj.dropwizardmysql.domain.Employee;
import com.aj.dropwizardmysql.mapper.EmployeeMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(EmployeeMapper.class)
public interface EmployeeDao {

    @SqlQuery("select * from employee;")
    public List<Employee> getEmployees();

    @SqlQuery("select * from employee where id = :id")
    public Employee getEmployee(@Bind("id") final int id);

    @SqlUpdate("insert into employee(name, department, salary) values(:name, :department, :salary)")
    void createEmployee(@BindBean final Employee employee);

    @SqlUpdate("update employee set name = coalesce(:name, name), " +
            " department = coalesce(:department, department), salary = coalesce(:salary, salary)" +
            " where id = :id")
    void editEmployee(@BindBean final Employee employee);

    @SqlUpdate("delete from employee where id = :id")
    int deleteEmployee(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}

