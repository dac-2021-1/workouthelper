/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Usuario;
import org.json.JSONObject;

/**
 *
 * @author marco
 */
@Stateless
@Path("/login")
public class LoginFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public LoginFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response login(Usuario entity) {
        try {
            Usuario u = em.createNamedQuery("Usuario.login", Usuario.class)
                    .setParameter("cpf", entity.getCpf())
                    .setParameter("senha", entity.getSenha())
                    .getSingleResult();
            System.out.println("service.LoginFacadeREST.login() " + u);
            String jwtToken = createToken(u);
            if (jwtToken != null && entity.getCpf().equals(u.getCpf())
                    && entity.getSenha().equals(u.getSenha())) {
                JSONObject jo = new JSONObject();
                jo.put("cpf", u.getCpf());
                jo.put("name", u.getNome());
                jo.put("id", u.getId());
                jo.put("token", jwtToken);
                if(u.getAluno() != null) jo.put("isAluno", true);
                else jo.put("isAluno", false);
                
                return Response.status(Response.Status.OK).entity(jo.toString()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Usuário e/ou senha inválidos").build();
            }
        } catch (Exception ex) {
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR
            ).entity(ex.getMessage())
                    .build();
        }
    }

    public String createToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("workouthelper");
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new Date());
            gc.add(Calendar.HOUR, 8);

            String token = JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(gc.getTime())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            return "";
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
