package com.example.springSecurity.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${security.jwt.private.key}")
    private String privateKey;
    @Value("{security.jwt.user.generator}")
    private String userGenerator;

    //creo tokens
    public String createToken(Authentication auth){ /// en auth voy a tener todos los datos del user para generar el token

        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        //traigo user y mantengo el contextholder para que no tenga que logear siempre
        String username = auth.getPrincipal().toString();

        String authorities = auth.getAuthorities()
                .stream() ///Transformo en stream para mapear como grantedauthority
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); ///Divido la secuencia de authorities con comas

        return JWT.create()
                .withIssuer(this.userGenerator) ///encargado de crear el token
                .withSubject(username)
                .withClaim("authorities",authorities)
                .withIssuedAt(new Date()) ///fecha de ahora
                .withExpiresAt(new Date(System.currentTimeMillis() + (30*60000))) ///Fecha de ahora + 30 mins para expirar el token
                .withJWTId(UUID.randomUUID().toString()) ///id del token
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier =  JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException e){
            throw new JWTVerificationException("Invalid token not authorized");
        }

    }

    ///metodo para obtener user
    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }
    ///Metodo para obtener claims
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }
    ///Metodo para obtener claim en especifico
    public Map<String, Claim> returnAllCollections(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
