package com.aj.dropwizardmysql.resource;

import com.aj.dropwizardmysql.service.EmployeeService;
import com.codahale.metrics.health.HealthCheck;

public class DropwizardMySQLHealthCheckResource extends HealthCheck {

    private static final String HEALTHY_MESSAGE = "The Dropwizard blog Service is healthy for read and write";
    private static final String UNHEALTHY_MESSAGE = "The Dropwizard blog Service is not healthy. ";

    private final EmployeeService employeeService;

    public DropwizardMySQLHealthCheckResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Result check() throws Exception {
        String mySqlHealthStatus = employeeService.performHealthCheck();

        if (mySqlHealthStatus == null) {
            return Result.healthy(HEALTHY_MESSAGE);
        } else {
            return Result.unhealthy(UNHEALTHY_MESSAGE , mySqlHealthStatus);
        }
    }
}