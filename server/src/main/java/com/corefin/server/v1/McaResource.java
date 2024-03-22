package com.corefin.server.v1;

import com.corefin.server.v1.request.CreateLoanRequest;
import com.corefin.server.v1.request.CreateMcaRequest;
import com.corefin.server.v1.response.GetLoanResponse;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Component
@Path("/mca")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class McaResource {

    private static final Logger LOGGER = Logger.getLogger(McaResource.class.getName());

    private McaResourceManager mcaResourceManager;
    @Inject
    public McaResource(McaResourceManager mcaResourceManager) {
        this.mcaResourceManager = mcaResourceManager;
    }

    @POST
    public String createLoan(@Valid CreateMcaRequest createMcaRequest) {
        return loanResourceManager.createLoan(createLoanRequest);
    }
    @GET
    @Path("/{loanId}")
    public GetLoanResponse getLoan(@PathParam("loanId") String loanId) {
        LOGGER.info("getLoan called for loan with id %s".formatted(loanId));
        return loanResourceManager.doGetLoan(loanId);
    }
}
