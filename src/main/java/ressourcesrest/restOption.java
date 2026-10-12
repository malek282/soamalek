package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("options")
public class restOption {
    public OptionBusiness optB = new OptionBusiness();

    // A.2 et A.3 : toutes les options, ou filtrées par domaine
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String domaine) {
        List<Option> l;
        if (domaine == null) {
            l = optB.getListeOptions();
        } else {
            l = optB.getOptionsByDomaine(domaine);
        }
        return Response.status(200).entity(l).build();
    }

    // A.6 : une option par son code
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optB.getOptionByCode(code);
        if (option == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(option).build();
    }

    // A.1 : créer une option
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (option == null) {
            return Response.status(400).build();
        }
        optB.addOption(option);
        return Response.status(201).entity(option).build();
    }

    // A.4 : supprimer une option
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optB.deleteOption(code)) {
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    // A.5 : modifier une option
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option == null) {
            return Response.status(400).build();
        }
        option.setCodeOption(code);
        if (optB.updateOption(code, option)) {
            return Response.status(200).entity(option).build();
        }
        return Response.status(404).build();
    }
}