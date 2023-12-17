package si.fri.rso.simplsrecka.lotteryticketcatalog.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import si.fri.rso.simplsrecka.lotteryticketcatalog.lib.LotteryTicket;
import si.fri.rso.simplsrecka.lotteryticketcatalog.services.beans.LotteryCatalogBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/lottery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Lottery ticket catalog", description = "APIs for lottery ticket catalog operations")
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, HEAD, OPTIONS", allowOrigin = "*")
public class LotteryCatalogResource {

    private Logger log = Logger.getLogger(LotteryCatalogResource.class.getName());

    @Inject
    private LotteryCatalogBean lotteryCatalogBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all lottery tickets.", summary = "Get all lottery tickets")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of lottery tickets",
                    content = @Content(schema = @Schema(implementation = LotteryTicket.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of tickets in list")}
            ),
            @APIResponse(responseCode = "500",
                    description = "Internal Server Error")
    })
    @GET
    public Response getLotteryTickets() {
        try {
            List<LotteryTicket> lotteryTickets = lotteryCatalogBean.getLotteryTicketsFilter(uriInfo);
            return Response.status(Response.Status.OK).entity(lotteryTickets).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching lottery tickets", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Get details for a specific lottery ticket.", summary = "Get ticket details")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Lottery ticket details",
                    content = @Content(schema = @Schema(implementation = LotteryTicket.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Ticket not found"),
            @APIResponse(responseCode = "500",
                    description = "Internal Server Error")
    })
    @GET
    @Path("/{ticketId}")
    public Response getLotteryTicket(@Parameter(description = "Ticket ID.", required = true)
                                     @PathParam("ticketId") Integer ticketId) {
        try {
            LotteryTicket lotteryTicket = lotteryCatalogBean.getLotteryTicket(ticketId);
            if (lotteryTicket == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.OK).entity(lotteryTicket).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching lottery ticket", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Add a new lottery ticket.", summary = "Add ticket")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Ticket successfully added"),
            @APIResponse(responseCode = "400",
                    description = "Invalid ticket data"),
            @APIResponse(responseCode = "500",
                    description = "Internal Server Error")
    })
    @POST
    public Response createLotteryTicket(@RequestBody(description = "DTO object with lottery ticket details.",
            required = true,
            content = @Content(schema = @Schema(implementation = LotteryTicket.class)))
                                        LotteryTicket lotteryTicket) {
        try {
            if (lotteryTicket == null || lotteryTicket.getName() == null || lotteryTicket.getDescription() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            lotteryTicket = lotteryCatalogBean.createLotteryTicket(lotteryTicket);
            return Response.status(Response.Status.CREATED).entity(lotteryTicket).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error adding lottery ticket", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Update details for a specific lottery ticket.", summary = "Update ticket")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ticket successfully updated"),
            @APIResponse(responseCode = "404",
                    description = "Ticket not found"),
            @APIResponse(responseCode = "400",
                    description = "Invalid ticket data"),
            @APIResponse(responseCode = "500",
                    description = "Internal Server Error")

    })
    @PUT
    @Path("/{ticketId}")
    public Response updateLotteryTicket(@Parameter(description = "Ticket ID.", required = true)
                                        @PathParam("ticketId") Integer ticketId,
                                        @RequestBody(description = "DTO object with updated ticket details.",
                                                required = true,
                                                content = @Content(schema = @Schema(implementation = LotteryTicket.class)))
                                        LotteryTicket lotteryTicket) {
        try {
            if (lotteryTicket == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            LotteryTicket updatedTicket = lotteryCatalogBean.updateLotteryTicket(ticketId, lotteryTicket);
            if (updatedTicket == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.OK).entity(updatedTicket).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating lottery ticket", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(description = "Delete a specific lottery ticket.", summary = "Delete ticket")
    @APIResponses({
            @APIResponse(responseCode = "204",
                    description = "Ticket successfully deleted"),
            @APIResponse(responseCode = "404",
                    description = "Ticket not found"),
            @APIResponse(responseCode = "500",
                    description = "Internal Server Error")
    })
    @DELETE
    @Path("/{ticketId}")
    public Response deleteLotteryTicket(@Parameter(description = "Ticket ID.", required = true)
                                        @PathParam("ticketId") Integer ticketId) {
        try {
            boolean deleted = lotteryCatalogBean.deleteLotteryTicket(ticketId);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting lottery ticket", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
