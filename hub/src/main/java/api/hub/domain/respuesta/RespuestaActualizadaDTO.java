package api.hub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO (Objeto de Transferencia de Datos) para representar una respuesta actualizada.
 */
public record RespuestaActualizadaDTO(
        @NotNull Long id,
        String solution,
        @NotNull Long usuario_Id,
        @NotNull Long topico_Id,
        LocalDateTime creationDate
) {
}
