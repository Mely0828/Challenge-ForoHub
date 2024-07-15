package api.hub.controller;

import api.hub.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name="bearer-key")
public class UsuarioControlador {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**************************************
     * REST API GET
     * Obtener todos los Usuarios paginados
     * ENDPOINT :
     * http://localhost:8080/usuario/usuarios
     ***************************************/
    @GetMapping("/usuarios")
    public ResponseEntity<Page<ListarUsuariosDTO>> listarUsuarios(@PageableDefault(size = 10) Pageable paged){
        return ResponseEntity.ok(usuarioRepository.findByActiveTrue(paged).map(ListarUsuariosDTO::new));
    }

    /************************************************
     * REST API PUT
     * Actualizar un usuario por ID
     * ENDPOINT :
     * http://localhost:8080/usuario/{id}
     *************************************************/
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizacionUsuario(@PathVariable Long id, @RequestBody @Valid ActualizacionUsuarioDTO actualizacionUsuarioDTO){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.actualizacionUsuario(actualizacionUsuarioDTO);
        return ResponseEntity.ok(new ActualizacionUsuarioDTO(usuario.getId(), usuario.getName(), usuario.getEmail()));
    }

    /************************************************
     * REST API DELETE
     * Eliminar un usuario por ID
     * ENDPOINT :
     * http://localhost:8080/usuario/{id}
     *************************************************/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.deactivateUser();
        return ResponseEntity.noContent().build();
    }

    /*******************************************
     * REST API GET
     * Obtener un Usuario por ID
     * ENDPOINT :
     * http://localhost:8080/usuario/{id}
     ********************************************/
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> obtenerUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var respuestaUsuarioDTO = new RespuestaUsuarioDTO(usuario.getId(), usuario.getName());
        return ResponseEntity.ok(respuestaUsuarioDTO);
    }
}
