package api.hub.domain.respuesta;

import api.hub.domain.topico.TopicoRepository;
import api.hub.domain.usuario.UsuarioRepository;
import api.hub.infra.errors.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RespuestaRepository repository;

    /**
     * Crea una nueva respuesta asociada a un tópico y usuario especificados.
     *
     * @param respuestaDTO DTO que contiene los datos de la respuesta a crear.
     * @return DTO que representa la respuesta creada.
     * @throws ValidacionDeIntegridad Si el usuario o el tópico especificados no están registrados en la base de datos.
     */
    public RespuestaCreadaDTO respuestaCreadaDTO(RespuestaDTO respuestaDTO) {
        // Verificar si el usuario existe en la base de datos
        if (!usuarioRepository.findById(respuestaDTO.usuario_Id()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID de usuario no está registrado en la base de datos. ");
        }
        
        // Verificar si el tópico existe en la base de datos
        if (!topicoRepository.findById(respuestaDTO.topico_Id()).isPresent()){
            throw new ValidacionDeIntegridad("Este id de tópico no está registrado en la base de datos. ");
        }
        
        // Obtener el usuario y el tópico asociados a la respuesta
        var usuario = usuarioRepository.findById(respuestaDTO.usuario_Id()).get();
        var topico = topicoRepository.findById(respuestaDTO.topico_Id()).get();

        // Crear la respuesta y guardarla en el repositorio
        var respuesta = new Respuesta(null, respuestaDTO.solution(), usuario, topico, respuestaDTO.creationDate());
        repository.save(respuesta);

        // Devolver DTO que representa la respuesta creada
        return new RespuestaCreadaDTO(respuesta);
    }
}
