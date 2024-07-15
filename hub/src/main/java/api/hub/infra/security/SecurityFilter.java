package api.hub.infra.security;

import api.hub.domain.usuario.Usuario;
import api.hub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para filtrar todas las peticiones entrantes
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");  // Obtiene el encabezado Authorization
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.replace("Bearer ", "");  // Extrae el token JWT
                String subject = tokenService.getSubject(token);  // Obtiene el sujeto (nombre de usuario) desde el token

                if (subject != null) {
                    UserDetails usuario = usuarioRepository.findByEmail(subject);  // Busca al usuario por su email (nombre de usuario)
                    if (usuario != null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);  // Configura la autenticación en el contexto de seguridad
                    } else {
                        throw new IllegalArgumentException("Usuario no encontrado por nombre de usuario: " + subject);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error de autenticación: " + e.getMessage(), e);  // Registra errores de autenticación
        }

        filterChain.doFilter(request, response);  // Continúa con la cadena de filtros
    }
}
