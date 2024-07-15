package api.hub.infra.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Obtenemos los mensajes de error de validación y los concatenamos en un solo mensaje
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // Creamos un objeto ApiError con el código de estado BAD_REQUEST y el mensaje de error
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMessage, ex);

        // Retornamos una respuesta con el código de estado y el cuerpo que contiene el error
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
