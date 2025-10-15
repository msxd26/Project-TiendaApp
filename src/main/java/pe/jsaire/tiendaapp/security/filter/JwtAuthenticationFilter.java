package pe.jsaire.tiendaapp.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.jsaire.tiendaapp.models.entities.Usuario;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static pe.jsaire.tiendaapp.security.filter.TokenJwtConfig.CONTENT_TYPE;
import static pe.jsaire.tiendaapp.security.filter.TokenJwtConfig.HEADER_AUTHORIZATION;
import static pe.jsaire.tiendaapp.security.filter.TokenJwtConfig.PREFIX_TOKEN;
import static pe.jsaire.tiendaapp.security.filter.TokenJwtConfig.SECRET_KEY;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Usuario usuario = null;
        String email = null;
        String password = null;

        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            email = usuario.getEmail();
            password = usuario.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User usuario = (User) authResult.getPrincipal();

        String email = usuario.getUsername();
        String password = usuario.getPassword();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims().add("authorities", new ObjectMapper().writeValueAsString(roles)).build();


        String token = Jwts.builder()
                .subject(email)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 36000000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("email", email);
        body.put("message", "Sesion iniciada con exito!");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

        response.setStatus(200);
        response.setContentType(CONTENT_TYPE);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error al intentar realizar el autenticacion con usuario o contraseña!");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }
}
