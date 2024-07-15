package api.hub.infra.security;

import api.hub.domain.usuario.Usuario;
import api.hub.domain.usuario.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para generar un token JWT para el usuario dado
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(usuario.getPassword());  // Algoritmo de encriptación usando la contraseña del usuario
            return JWT.create()
                    .withIssuer("api")  // Emisor del token
                    .withSubject(usuario.getUsername())  // Sujeto del token (nombre de usuario)
                    .withClaim("id", usuario.getId())  // Añade un claim personalizado al token (ID del usuario)
                    .withExpiresAt(expirationdate())  // Fecha de expiración del token
                    .sign(algorithm);  // Firma el token con el algoritmo especificado
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al crear el token JWT", exception);
        }
    }

    // Método para obtener el sujeto (username) desde un token JWT
    public String getSubject(String token){
        if (token == null) {
            throw new IllegalArgumentException("El token es nulo.");
        }
        try {
            DecodedJWT decodedJWT = JWT.decode(token);  // Decodifica el token JWT
            String username = decodedJWT.getSubject();  // Obtiene el sujeto (nombre de usuario) del token decodificado
            if (username == null) {
                throw new IllegalArgumentException("Token no válido: Asunto no encontrado");
            }
            UserDetails usuario = usuarioRepository.findByEmail(username);  // Busca al usuario por su nombre de usuario (email)
            if (usuario == null) {
                throw new IllegalArgumentException("Usuario no encontrado por nombre de usuario: " + username);
            }

            Algorithm algorithm = Algorithm.HMAC256(usuario.getPassword());  // Algoritmo de verificación usando la contraseña del usuario
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("api")  // Valida que el emisor del token sea "api"
                    .build()
                    .verify(token);  // Verifica la firma del token

            return verifier.getSubject();  // Devuelve el sujeto (nombre de usuario) verificado
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Token no válido: " + e.getMessage(), e);
        }
    }

    // Método privado para calcular la fecha de expiración del token (2 horas desde ahora)
    private Instant expirationdate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
