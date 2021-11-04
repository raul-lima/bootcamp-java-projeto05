package br.com.alura.bootcamp.livraria.infra.security;

import br.com.alura.bootcamp.livraria.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    // anotação lê a propriedade no application.properties
    @Value("${jjwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {

        Usuario logado = (Usuario) authentication.getPrincipal();

        return Jwts
                .builder()
                .setSubject(logado.getId().toString())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token){

        try{
            Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return  false;
        }

    }

    public Long extrairIdUsuario(String token) {

            Claims claims = Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
