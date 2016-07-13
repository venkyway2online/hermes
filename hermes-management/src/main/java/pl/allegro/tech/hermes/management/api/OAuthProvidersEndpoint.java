package pl.allegro.tech.hermes.management.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.allegro.tech.hermes.api.OAuthProvider;
import pl.allegro.tech.hermes.api.PatchData;
import pl.allegro.tech.hermes.management.api.auth.Roles;
import pl.allegro.tech.hermes.management.domain.oauth.OAuthProviderService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;

@Component
@Path("/oAuthProviders")
@Api(value = "/oAuthProviders", description = "Operations on OAuth providers")
public class OAuthProvidersEndpoint {

    private final OAuthProviderService service;

    @Autowired
    public OAuthProvidersEndpoint(OAuthProviderService service) {
        this.service = service;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "List OAuth providers", httpMethod = HttpMethod.GET)
    public List<String> list() {
        return service.listOAuthProviderNames();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{oAuthProviderName}")
    @ApiOperation(value = "OAuth provider details", httpMethod = HttpMethod.GET)
    public OAuthProvider get(@PathParam("oAuthProviderName") String oAuthProviderName) {
        return service.getOAuthProviderDetails(oAuthProviderName);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed(Roles.ADMIN)
    @ApiOperation(value = "Create OAuth provider", httpMethod = HttpMethod.POST)
    public Response create(OAuthProvider oAuthProvider) {
        service.createOAuthProvider(oAuthProvider);
        return status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed(Roles.ADMIN)
    @Path("/{oAuthProviderName}")
    @ApiOperation(value = "Update OAuth provider", httpMethod = HttpMethod.PUT)
    public Response update(@PathParam("oAuthProviderName") String oAuthProviderName, PatchData patch) {
        service.updateOAuthProvider(oAuthProviderName, patch);
        return status(Response.Status.OK).build();
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @RolesAllowed(Roles.ADMIN)
    @Path("/{oAuthProviderName}")
    @ApiOperation(value = "Remove OAuth provider", httpMethod = HttpMethod.DELETE)
    public Response remove(@PathParam("oAuthProviderName") String oAuthProviderName) {
        service.removeOAuthProvider(oAuthProviderName);
        return status(Response.Status.OK).build();
    }
}
