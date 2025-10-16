package pe.jsaire.tiendaapp.utils.validations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.CategoriaService;

@Component
@RequiredArgsConstructor
public class ExistByCategoriaValidation implements ConstraintValidator<isExistsByCategoria, String> {

    private final CategoriaService categoriaService;


    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext constraintValidatorContext) {
        if (nombre == null || nombre.isBlank()) {
            return false;
        }
        return !categoriaService.existsByNombre(nombre);
    }
}
