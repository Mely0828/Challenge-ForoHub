package api.hub.domain.topico;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para la creación de un nuevo tópico.
 * Contiene los datos necesarios para crear un nuevo tópico en el sistema.
 */
public record TopicoDTO(
        // El título del tópico, no puede estar vacío y debe ser único.
        @NotNull(message = "El título no se puede repetir.")
        String title,

        // El mensaje del tópico, no puede estar vacío y debe ser apropiado.
        @NotNull(message = "Utilice un lenguaje apropiado en el mensaje que no supere los 700 caracteres de longitud.")
        String message,

        // El estado del tópico, debe ser uno de los enumerados en la enumeración 'Status'.
        @NotNull(message = "Seleccione uno de los Estados ´ACTIVO´ o ´INACTIVO´")
        Status status,

        // El ID del usuario autor del tópico.
        @NotNull(message = "Utilice su ID de autor de usuario_Id")
        Long usuario_Id,

        // El curso al que está relacionado el tópico.
        @NotNull(message = "Recuerda utilizar el curso apropiado para tu publicación.")
        String curso,

        // La fecha de creación del tópico, puede ser nula si se crea automáticamente.
        LocalDateTime date
) {
}
