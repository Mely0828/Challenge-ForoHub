package api.hub.domain.topico;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para actualizar un tópico existente.
 * Contiene los campos que pueden ser actualizados en un tópico.
 */
public record TopicoActualizadoDTO(
        // El ID del tópico a actualizar, no puede ser nulo.
        @NotNull Long id,

        // El nuevo título del tópico, puede ser nulo si no se actualiza.
        String title,

        // El nuevo mensaje del tópico, puede ser nulo si no se actualiza.
        String message,

        // El nuevo estado del tópico, puede ser nulo si no se actualiza.
        Status status,

        // El nuevo ID del usuario autor del tópico, no puede ser nulo.
        @NotNull Long usuario_Id,

        // El nuevo curso del tópico, puede ser nulo si no se actualiza.
        String curso,

        // La nueva fecha de actualización del tópico, puede ser nula si no se actualiza.
        LocalDateTime date
) {
}
