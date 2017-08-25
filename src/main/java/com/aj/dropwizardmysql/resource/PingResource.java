package com.aj.dropwizardmysql.resource;

import com.aj.dropwizardmysql.domain.PingRequest;
import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "ping", description = "Ping Resource for checking if application is up")
public class PingResource {

    private static final Logger logger = LoggerFactory.getLogger(PingResource.class);

	@Timed
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value ="ping")
	public Response ping() {
		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "pong");
		return Response.ok(response).build();
	}


    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response send(PingRequest pingRequest) throws Exception {
        logger.info("Request received is: {}", pingRequest );
        Map<String, String> response = new HashMap<>();
        response.put("message", "");
        if("ping".equalsIgnoreCase(pingRequest.getInput())) {
            response.put("message", "pong");
        }
        return Response.ok(response).build();
    }
}
