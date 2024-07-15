package api.hub.domain.topico;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para listar tópicos.
 */
public record ListarTopicosDTO(
        Long id,
        String title,
        String message,
        Status status,
        Long usuario_Id,
        String curso,
        LocalDateTime date
) {
    /**
     * Constructor que inicializa un objeto ListarTopicosDTO a partir de un objeto Topico.
     *
     * @param topico Objeto Topico del cual se extraen los datos para inicializar el DTO.
     */
    public ListarTopicosDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getStatus(),
                topico.getAuthor().getId(),
                topico.getCourse(),
                topico.getDate()
        );
    }
}
