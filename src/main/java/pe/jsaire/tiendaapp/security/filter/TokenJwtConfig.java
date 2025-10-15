package pe.jsaire.tiendaapp.security.filter;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    static final String PREFIX_TOKEN = "Bearer ";
    static final String HEADER_AUTHORIZATION = "Authorization";
    static final String CONTENT_TYPE = "application/json";

}
