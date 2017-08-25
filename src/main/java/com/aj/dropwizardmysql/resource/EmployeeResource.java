package com.aj.dropwizardmysql.resource;

import com.aj.dropwizardmysql.domain.Employee;
import com.aj.dropwizardmysql.service.EmployeeService;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "employee", description = "Employee Resource for performing CRUD operations on Employee Table")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GET
    @Timed
    public Response getEmployees() {
        return Response.ok(employeeService.getEmployees()).build();
    }

    @GET
    @Timed
    @Path("{id}")
    public Response getEmployee(@PathParam("id") final int id) {
        return Response.ok(employeeService.getEmployee(id)).build();
    }

    @POST
    @Timed
    public Response createEmployee(@NotNull @Valid final Employee employee) {
        Employee employeeCreate = new Employee(employee.getName(),employee.getDepartment(),employee.getSalary());
        return Response.ok(employeeService.createEmployee(employeeCreate)).build();
    }

    @PUT
    @Timed
    @Path("{id}")
    public Response editPart(@NotNull @Valid final Employee employee,
                             @PathParam("id") final int id) {
        employee.setId(id);
        return Response.ok(employeeService.editEmployee(employee)).build();
    }

    @DELETE
    @Timed
    @Path("{id}")
    public Response deletePart(@PathParam("id") final int id) {
        Map<String, String> response = new HashMap<String, String>();
        response.put("status", employeeService.deleteEmployee(id));
        return Response.ok(response).build();
    }
}

