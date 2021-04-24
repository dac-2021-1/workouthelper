/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Aluno;
import model.Professor;
import model.Usuario;

/**
 *
 * @author marco
 */
@Stateless
@Path("/usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @EJB
    private AlunoFacadeREST alunoFacadeREST;

    @EJB
    private ProfessorFacadeREST professorFacadeREST;

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createUser(Usuario entity) {
        Aluno aluno = entity.getAluno();
        entity.setAluno(null);
        Professor professor = entity.getProfessor();
        entity.setProfessor(null);
        super.createUser(entity);
        em.flush();
        if (professor != null) {
            professor.setUsuario(entity);
            professorFacadeREST.createUser(professor);
        } else if(aluno != null) {
            aluno.setUsuario(entity);
            alunoFacadeREST.createUser(aluno);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Integer id, Usuario entity) {
        Usuario u = em.createNamedQuery("Usuario.findById", Usuario.class)
                    .setParameter("id", id)
                    .getSingleResult();

        entity.setCpf(u.getCpf());
        entity.setDataInicio(u.getDataInicio());
        entity.setId(id);
        entity.setNome(u.getNome());
        entity.setSexo(u.getSexo());
        entity.setDataNasc(u.getDataNasc());

        return super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Integer id) {
        return super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
