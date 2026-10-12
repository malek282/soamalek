package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("etudiants")
public class restEtudiant {

    private EtudiantBusiness business = new EtudiantBusiness();
    private OptionBusiness optionBusiness = new OptionBusiness();

    // B1 : POST /etudiants
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouter(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (business.addEtudiant(etudiant)) {
            return Response.status(Response.Status.CREATED).entity(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // option inexistante
    }

    // B2 : GET /etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response lister() {
        return Response.ok(business.getAllEtudiants()).build();
    }

    // B3 : GET /etudiants/{id}
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        Etudiant e = business.getEtudiantByIdentifiant(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(e).build();
    }

    // B4 : DELETE /etudiants/{id}
    @DELETE
    @Path("{id}")
    public Response supprimer(@PathParam("id") String id) {
        if (business.deleteEtudiant(id)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B5 : PUT /etudiants/{id}
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant != null && business.updateEtudiant(id, etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B6 : GET /etudiants/option?codeOption=1  (XML)
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response parOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        EtudiantList liste = new EtudiantList();
        liste.setEtudiants(business.getEtudiantsByOption(option)); // à adapter, voir ci-dessous
        return Response.ok(liste).build();
    }
}