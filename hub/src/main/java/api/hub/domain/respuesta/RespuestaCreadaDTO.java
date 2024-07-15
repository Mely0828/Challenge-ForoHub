package api.hub.domain.respuesta;

import java.time.LocalDateTime;

/**
 * DTO (Objeto de Transferencia de Datos) para representar una respuesta creada.
 */
public record RespuestaCreadaDTO(
        Long id,
        String solution,
        Long usuario_Id,
        Long topico_Id,
        LocalDateTime creationDate
) {
    /**
     * Constructor para crear un DTO de respuesta a partir de una instancia de Respuesta.
     *
     * @param rVerified La respuesta verificada de la cual se extraen los datos.
     */
    public RespuestaCreadaDTO(Respuesta rVerified) {
        this(
                rVerified.getId(),
                rVerified.getSolution(),
                rVerified.getAuthor().getId(),
                rVerified.getTopico().getId(),
                rVerified.getCreationDate()
        );
    }
}
