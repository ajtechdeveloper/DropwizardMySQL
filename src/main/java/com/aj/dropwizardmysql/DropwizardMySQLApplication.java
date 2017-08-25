package com.aj.dropwizardmysql;

import com.aj.dropwizardmysql.resource.DropwizardMySQLHealthCheckResource;
import com.aj.dropwizardmysql.resource.EmployeeResource;
import com.aj.dropwizardmysql.resource.PingResource;
import com.aj.dropwizardmysql.service.EmployeeService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import org.skife.jdbi.v2.DBI;


public class DropwizardMySQLApplication extends Application<DropwizardMySQLConfiguration> {

    private static final Logger logger = LoggerFactory.getLogger(DropwizardMySQLApplication.class);
	private static final String SQL = "sql";
	private static final String DROPWIZARD_MYSQL_SERVICE = "Dropwizard MySQL Service";

	public static void main(String[] args) throws Exception {
		new DropwizardMySQLApplication().run("server", args[0]);
	}

    @Override
    public void initialize(Bootstrap<DropwizardMySQLConfiguration> b) {
    }

	@Override
	public void run(DropwizardMySQLConfiguration config, Environment env)
			throws Exception {
        // Datasource configuration
        final DataSource dataSource =
                config.getDataSourceFactory().build(env.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        // Register Health Check
        DropwizardMySQLHealthCheckResource healthCheck =
                new DropwizardMySQLHealthCheckResource(dbi.onDemand(EmployeeService.class));
        env.healthChecks().register(DROPWIZARD_MYSQL_SERVICE, healthCheck);
	    logger.info("Registering RESTful API resources");
		env.jersey().register(new PingResource());
        env.jersey().register(new EmployeeResource(dbi.onDemand(EmployeeService.class)));
	}
}
