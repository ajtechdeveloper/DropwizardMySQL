package com.aj.dropwizardmysql.service;

import com.aj.dropwizardmysql.dao.EmployeeDao;
import com.aj.dropwizardmysql.domain.Employee;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import java.util.List;
import java.util.Objects;

public abstract class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private static final String DATABASE_ACCESS_ERROR =
            "Could not reach the MySQL database. The database may be down or there may be network connectivity issues. Details: ";
    private static final String DATABASE_CONNECTION_ERROR =
            "Could not create a connection to the MySQL database. The database configurations are likely incorrect. Details: ";
    private static final String UNEXPECTED_DATABASE_ERROR =
            "Unexpected error occurred while attempting to reach the database. Details: ";
    private static final String SUCCESS = "Success";
    private static final String UNEXPECTED_DELETE_ERROR = "An unexpected error occurred while deleting employee.";
    private static final String EMPLOYEE_NOT_FOUND = "Employee id %s not found.";

    @CreateSqlObject
    abstract EmployeeDao employeeDao();

    public List<Employee> getEmployees() {
        return employeeDao().getEmployees();
    }

    public Employee getEmployee(int id) {
        Employee employee = employeeDao().getEmployee(id);
        if (Objects.isNull(employee)) {
            throw new WebApplicationException(String.format(EMPLOYEE_NOT_FOUND, id), Status.NOT_FOUND);
        }
        return employee;
    }

    public Employee createEmployee(Employee employee) {
        employeeDao().createEmployee(employee);
        return employeeDao().getEmployee(employeeDao().lastInsertId());
    }

    public Employee editEmployee(Employee employee) {
        if (Objects.isNull(employeeDao().getEmployee(employee.getId()))) {
            throw new WebApplicationException(String.format(EMPLOYEE_NOT_FOUND, employee.getId()),
                    Status.NOT_FOUND);
        }
        employeeDao().editEmployee(employee);
        return employeeDao().getEmployee(employee.getId());
    }

    public String deleteEmployee(final int id) {
        int result = employeeDao().deleteEmployee(id);
        logger.info("Result in EmployeeService.deleteEmployee is: {}", result );
        switch (result) {
            case 1:
                return SUCCESS;
            case 0:
                throw new WebApplicationException(String.format(EMPLOYEE_NOT_FOUND, id), Status.NOT_FOUND);
            default:
                throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Status.INTERNAL_SERVER_ERROR);
        }
    }

    public String performHealthCheck() {
        try {
            employeeDao().getEmployees();
        } catch (UnableToObtainConnectionException ex) {
            return checkUnableToObtainConnectionException(ex);
        } catch (UnableToExecuteStatementException ex) {
            return checkUnableToExecuteStatementException(ex);
        } catch (Exception ex) {
            return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

    private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
        if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
            return DATABASE_ACCESS_ERROR + ex.getCause().getLocalizedMessage();
        } else if (ex.getCause() instanceof java.sql.SQLException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
    }

    private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
        if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
            return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
        } else {
            return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
        }
    }
}
