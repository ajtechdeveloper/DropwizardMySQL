package com.aj.dropwizardmysql;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DropwizardMySQLConfiguration extends Configuration {
    private static final String DATABASE = "database";

    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @JsonProperty(DATABASE)
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    @JsonProperty(DATABASE)
    public void setDataSourceFactory(final DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
}
