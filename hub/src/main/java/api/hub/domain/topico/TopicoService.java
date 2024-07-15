package api.hub.domain.topico;

import api.hub.domain.usuario.Usuario;
import api.hub.domain.usuario.UsuarioRepository;
import api.hub.infra.errors.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RespuestaTopicoDTO topicoCreado(TopicoDTO topicoDTO) {
        // Verificar si el usuario existe en la base de datos
        Usuario usuario = usuarioRepository.findById(topicoDTO.usuario_Id())
                .orElseThrow(() -> new ValidacionDeIntegridad("Este ID de usuario no está registrado en la base de datos."));

        // Verificar si ya existe un tópico con el mismo título
        String title = topicoDTO.title();
        if (title != null && topicoRepository.existsByTitleIgnoreCase(title)) {
            throw new ValidacionDeIntegridad("Este título ya está presente en la base de datos. Por favor revise el tópico existente.");
        }

        // Verificar si ya existe un tópico con el mismo mensaje
        String message = topicoDTO.message();
        if (message != null && topicoRepository.existsByMessageIgnoreCase(message)) {
            throw new ValidacionDeIntegridad("Este mensaje ya está presente en la base de datos. Por favor revise el tópico existente.");
        }

        // Crear el objeto Tópico y guardarlo en la base de datos
        Topico topico = new Topico(null, title, message, topicoDTO.date(), topicoDTO.status(), usuario, topicoDTO.curso());
        topicoRepository.save(topico);

        return new RespuestaTopicoDTO(topico);
    }
}
