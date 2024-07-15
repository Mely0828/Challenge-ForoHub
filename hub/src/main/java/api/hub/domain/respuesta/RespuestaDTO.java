package api.hub.domain.respuesta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import api.hub.domain.usuario.Usuario;
import api.hub.domain.topico.Topico;

import java.time.LocalDateTime;

/**
 * DTO (Objeto de Transferencia de Datos) para crear una respuesta.
 */
public record RespuestaDTO(
        @NotBlank(message = "La solución no puede estar en blanco.")
        String solution,
        @NotNull(message = "El ID de usuario no puede ser nulo.")
        @Valid
        Long usuario_Id,
        @NotNull(message = "El ID de tópico no puede ser nulo.")
        @Valid
        Long topico_Id,
        LocalDateTime creationDate) {
}
