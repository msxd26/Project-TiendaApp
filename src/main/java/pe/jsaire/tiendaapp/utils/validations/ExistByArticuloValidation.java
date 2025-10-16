package pe.jsaire.tiendaapp.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.ArticuloService;

@Component
@RequiredArgsConstructor
public class ExistByArticuloValidation implements ConstraintValidator<isExistByNombreArticulo, String> {

    private final ArticuloService articuloService;


    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext constraintValidatorContext) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        return !articuloService.existsByNombre(nombre);
    }
}
