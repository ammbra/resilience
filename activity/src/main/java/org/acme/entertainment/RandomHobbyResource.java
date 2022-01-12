package org.acme.entertainment;

import io.github.bucket4j.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.util.UUID;

@Path("activity")
public class RandomHobbyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomHobbyResource.class);

    @ConfigProperty(name = "worker.cloud.id", defaultValue = "unknown")
    String cloudId;

    private final Bucket bucket;


    private final String ID;


    @RestClient
    @Inject
    ActivityService service;


    public RandomHobbyResource() {
        ID = "worker-quarkus-" + UUID.randomUUID()
                .toString().substring(0, 4);
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));

        this.bucket = Bucket4j.builder().addLimit(limit)
                .build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RandomHobby getRandomHobby() {
        RandomHobby hobby = service.getActivity();
        hobby.cloudId = cloudId;
        hobby.workerId = ID;
        return hobby;
    }

    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public BasicHobby getHobbyByKey(@PathParam("key") long key) {
        return service.getActivityByKey(key);
    }

    @GET
    @Path("/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public BasicHobby getHobbyByType(@PathParam("type") String type) {
        return service.getActivityByType(type);
    }

    @GET
    @Path("limited/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLimitedHobbyByKey(@PathParam("key") long key) {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return  Response.status(Response.Status.OK)
                    .header("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()))
                    .entity(service.getActivityByKey(key)).build();
        }

        long waitForRefill = probe.getNanosToWaitForRefill()/ 1_000_000_000;
        return Response
                .status(Response.Status.TOO_MANY_REQUESTS)
                .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill))
                .build();
    }

    @GET
    @Path("{issue}/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHobbyByKey(@PathParam("issue") String issue, @PathParam("key") long key) {
        return switch(issue){
            case "timeout" -> timeout(key);
            case "notavailable" -> invokeServiceUnavailable();
            default -> Response.status(Response.Status.OK)
                        .entity(service.getActivityByKey(key)).build();
        };
    }

    private Response timeout(long key) {
        logger.debug(String.format("Thread interrupted on %s", cloudId));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.info("Thread interrupted");
        }

        return Response.status(Response.Status.GATEWAY_TIMEOUT)
                .entity(service.getActivityByKey(key)).build();
    }

    private Response invokeServiceUnavailable() {
        logger.debug(String.format("Misbehaving %s", cloudId));
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }



}