package pe.jsaire.tiendaapp.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jsaire.tiendaapp.models.repositories.ArticuloRepository;

@Component
@RequiredArgsConstructor
public class ExistByNombreValidation implements ConstraintValidator<isExistByNombre, String> {

    private final ArticuloRepository articuloRepository;


    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext constraintValidatorContext) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        return !articuloRepository.existsByNombre(nombre);
    }
}
