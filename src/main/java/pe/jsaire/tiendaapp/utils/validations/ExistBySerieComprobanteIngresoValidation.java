package pe.jsaire.tiendaapp.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jsaire.tiendaapp.infraestructures.abstract_services.IngresoService;

@Component
@RequiredArgsConstructor
public class ExistBySerieComprobanteIngresoValidation implements ConstraintValidator<isExistsBySerieComprobanteIngreso, String> {

    private final IngresoService ingresoService;


    @Override
    public boolean isValid(String serie, ConstraintValidatorContext constraintValidatorContext) {

        if (serie == null || serie.isBlank()) {
            return false;
        }

        return !ingresoService.existsIngresoBySerieComprobante(serie);
    }
}
