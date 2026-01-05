package org.unina.bugboard.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility per la gestione dei token JWT.
 * Fornisce metodi per generare, validare ed estrarre informazioni dai token
 * JWT.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    // da cambiare appena lo mettiamo in produzione, trasformiamo in variabile
    // d'ambiente
    @Value("${bugboard.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bugboard.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Recupera la chiave segreta per firmare i token JWT.
     * Decodifica la chiave segreta configurata in base64.
     *
     * @return La chiave segreta (SecretKey).
     */
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Genera un token JWT per l'utente autenticato.
     *
     * @param authentication L'oggetto Authentication contenente i dettagli
     *                       dell'utente.
     * @return Una stringa rappresentante il token JWT generato.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Estrae l'username dal token JWT.
     *
     * @param token Il token JWT da cui estrarre l'username.
     * @return L'username (email) contenuto nel token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Valida il token JWT verificandone la firma e la scadenza.
     *
     * @param authToken Il token JWT da validare.
     * @return true se il token è valido, false altrimenti.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
