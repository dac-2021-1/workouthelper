/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author marco
 */
@Provider
public class TokenFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String currentPath = requestContext.getUriInfo().getRequestUri().getPath();

        if (currentPath.contains("resources") && !currentPath.contains("login")) {
            String auth = requestContext.getHeaderString("auth");
            if (auth == null || !decodeToken(auth)) {
                System.out.println("service.TokenFilter.filter() ENTREI NO IF PARA NEGAR");
                requestContext
                        .abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                .build());
            }
        }
    }

    public Boolean decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("workouthelper");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("service.TokenFilter.decodeToken() " + jwt);
            return true;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            System.out.println("service.TokenFilter.decodeToken() " + exception.getMessage());
            return false;
        }
    }
}
