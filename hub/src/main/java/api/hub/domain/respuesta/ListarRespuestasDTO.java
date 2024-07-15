package api.hub.domain.respuesta;

import java.time.LocalDateTime;

/**
 * DTO para listar respuestas en un formato simplificado.
 */
public record ListarRespuestasDTO(
        Long id,
        String solution,
        Long usuario_Id,
        Long topico_Id,
        LocalDateTime creationDate
) {
    /**
     * Constructor que crea un ListarRespuestasDTO a partir de un objeto Respuesta.
     * @param respuesta Objeto Respuesta del cual se extraen los datos.
     */
    public ListarRespuestasDTO(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getSolution(),
                respuesta.getAuthor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getCreationDate()
        );
    }
}
