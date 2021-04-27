/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import model.RlTreinoExercico;
import model.RlTreinoExercicoPK;

/**
 *
 * @author marco
 */
@Stateless
@Path("/rltreinoexercico")
public class RlTreinoExercicoFacadeREST extends AbstractFacade<RlTreinoExercico> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    private RlTreinoExercicoPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idTreino=idTreinoValue;idExercicio=idExercicioValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        model.RlTreinoExercicoPK key = new model.RlTreinoExercicoPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idTreino = map.get("idTreino");
        if (idTreino != null && !idTreino.isEmpty()) {
            key.setIdTreino(new java.lang.Integer(idTreino.get(0)));
        }
        java.util.List<String> idExercicio = map.get("idExercicio");
        if (idExercicio != null && !idExercicio.isEmpty()) {
            key.setIdExercicio(new java.lang.Integer(idExercicio.get(0)));
        }
        return key;
    }

    public RlTreinoExercicoFacadeREST() {
        super(RlTreinoExercico.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(RlTreinoExercico entity) {
        if (entity.getRlTreinoExercicoPK() == null) {
            RlTreinoExercicoPK pk = new RlTreinoExercicoPK();
            pk.setIdTreino(entity.getTreino().getId());
            pk.setIdExercicio(entity.getExercicio().getId());
            entity.setRlTreinoExercicoPK(pk);
        }
        return super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") PathSegment id, RlTreinoExercico entity) {
        return super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("id") PathSegment id) {
        model.RlTreinoExercicoPK key = getPrimaryKey(id);
        return super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public RlTreinoExercico find(@PathParam("id") PathSegment id) {
        model.RlTreinoExercicoPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<RlTreinoExercico> findAll(@QueryParam("treino") Integer idTreino) {
        List<RlTreinoExercico> lista = super.findAll();
        if(idTreino != null){
            List<RlTreinoExercico> filtered = lista.stream()
                    .filter(li -> Objects.equals(li.getRlTreinoExercicoPK().getIdTreino(), idTreino))
                    .collect(Collectors.toList());
            return filtered;
        }
        return lista;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<RlTreinoExercico> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
