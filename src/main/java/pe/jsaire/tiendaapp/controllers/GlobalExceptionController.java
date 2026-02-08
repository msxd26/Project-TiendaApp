package pe.jsaire.tiendaapp.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pe.jsaire.tiendaapp.models.dto.response.ErrorResponse;
import pe.jsaire.tiendaapp.utils.exceptions.ArticuloNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.CategoriaNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.IngresoNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.PersonaNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.StockInsuficienteException;
import pe.jsaire.tiendaapp.utils.exceptions.UsuarioNotFoundException;
import pe.jsaire.tiendaapp.utils.exceptions.VentaNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({ArticuloNotFoundException.class,
            CategoriaNotFoundException.class,
            VentaNotFoundException.class,
            PersonaNotFoundException.class,
            IngresoNotFoundException.class,
            UsuarioNotFoundException.class,
            StockInsuficienteException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundOrBusiness(Exception ex) {
        HttpStatus status = ex instanceof StockInsuficienteException
                ? HttpStatus.CONFLICT
                : HttpStatus.NOT_FOUND;
        ErrorResponse error = ErrorResponse.builder()
                .status(status.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(HttpMessageNotReadableException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Cuerpo de la petición inválido o mal formado: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> details = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            details.put(fieldName, errorMessage);
        });

        var error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validación, revise los campos.")
                .timestamp(LocalDateTime.now())
                .details(details)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException exception) {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
