package api.hub.controller;

import api.hub.domain.usuario.Usuario;
import api.hub.domain.usuario.UsuarioDTO;
import api.hub.infra.security.JWTTokenDTO;
import api.hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionControlador {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity usuarioAutenticacion(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        // Crear objeto de autenticación con el correo y contraseña recibidos
        Authentication authToken = new UsernamePasswordAuthenticationToken(usuarioDTO.email(), usuarioDTO.password());
        
        // Autenticar al usuario usando el AuthenticationManager
        Authentication autenticacionUsuario = authenticationManager.authenticate(authToken);
        
        // Generar token JWT si la autenticación es exitosa
        String token = tokenService.generarToken((Usuario) autenticacionUsuario.getPrincipal());
        
        // Devolver el token JWT en la respuesta
        return ResponseEntity.ok(new JWTTokenDTO(token));
    }
}
