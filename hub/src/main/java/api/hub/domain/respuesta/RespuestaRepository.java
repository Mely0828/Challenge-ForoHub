package api.hub.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para gestionar entidades de Respuesta.
 */
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    /**
     * Encuentra respuestas activas paginadas.
     *
     * @param pageable Configuración de paginación.
     * @return Página de respuestas activas.
     */
    Page<Respuesta> findByActiveTrue(Pageable pageable);
}
