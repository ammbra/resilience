package org.acme.entertainment;

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
@Path("/api/activity")
public interface ActivityService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 5, delay = 100)
    @Timeout(250)
    @Fallback(DefaultBasicHobby.class)
    BasicHobby getActivityByType(@QueryParam("type") String type);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 5, delay = 100)
    @Fallback(DefaultRandomHobby.class)
    RandomHobby getActivity();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "activity")
    BasicHobby getActivityByKey(@CacheKey @QueryParam("key") long key);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    CompleteHobby getActivityByAccessibility(@QueryParam("minaccessibility") double minaccessibility, @QueryParam("maxaccessibility") double maxaccessibility);


    record DefaultBasicHobby() implements FallbackHandler<BasicHobby> {
        @Override
        public BasicHobby handle(ExecutionContext executionContext) {
            return BasicHobby.empty();
        }
    }

    record DefaultRandomHobby() implements FallbackHandler<RandomHobby> {
        @Override
        public RandomHobby handle(ExecutionContext executionContext) {
            return RandomHobby.empty();
        }
    }
}
