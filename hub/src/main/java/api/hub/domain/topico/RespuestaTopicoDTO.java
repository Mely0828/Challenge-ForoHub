package api.hub.domain.topico;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) que representa la respuesta de un tópico para ser enviado como respuesta a las solicitudes.
 */
public record RespuestaTopicoDTO(
        Long id,
        String title,
        String message,
        Status status,
        Long usuario_Id,
        String curso,
        LocalDateTime date) {

    /**
     * Constructor que crea un objeto RespuestaTopicoDTO a partir de un objeto Topico.
     *
     * @param topicoId Objeto Topico del cual se extraen los datos para construir este DTO.
     */
    public RespuestaTopicoDTO(Topico topicoId) {
        this(
                topicoId.getId(),
                topicoId.getTitle(),
                topicoId.getMessage(),
                topicoId.getStatus(),
                topicoId.getAuthor().getId(),
                topicoId.getCourse(),
                topicoId.getDate());
    }
}
