package api.hub.domain.respuesta;

import api.hub.domain.topico.Topico;
import api.hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Clase que representa una respuesta en el dominio del negocio.
 */
@Entity(name="Respuesta")
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate; // Fecha y hora de creación de la respuesta.
    private String solution; // Solución proporcionada en la respuesta.

    @OneToOne
    @JoinColumn(name="author", referencedColumnName="id")
    private Usuario author; // Usuario que proporcionó la respuesta.

    @OneToOne
    @JoinColumn(name="topico", referencedColumnName="id")
    private Topico topico; // Tópico al cual pertenece la respuesta.

    private boolean active; // Indicador de si la respuesta está activa o no en el sistema.

    /**
     * Constructor de la clase Respuesta.
     * @param id ID único de la respuesta.
     * @param solution Solución proporcionada en la respuesta.
     * @param usuario Usuario que proporcionó la respuesta.
     * @param topico Tópico al cual pertenece la respuesta.
     * @param creationDate Fecha y hora de creación de la respuesta.
     */
    public Respuesta(Long id, String solution, Usuario usuario, Topico topico, LocalDateTime creationDate) {
        this.id = id;
        this.solution = solution;
        this.author = usuario;
        this.topico = topico;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Método para actualizar los datos de una respuesta.
     * @param respuestaActualizadaDTO DTO con los datos actualizados de la respuesta.
     */
    public void respuestaActualizada(RespuestaActualizadaDTO respuestaActualizadaDTO) {
        if (respuestaActualizadaDTO.solution() != null){
            this.solution = respuestaActualizadaDTO.solution();
        }
    }

    /**
     * Método para desactivar una respuesta.
     */
    public void diactivateResponse() {
        this.active = false;
    }
}
