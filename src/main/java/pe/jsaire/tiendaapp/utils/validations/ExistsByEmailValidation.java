package pe.jsaire.tiendaapp.utils.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jsaire.tiendaapp.models.repositories.UsuarioRepository;

@Component
@RequiredArgsConstructor
public class ExistsByEmailValidation implements ConstraintValidator<isExistsByEmail, String> {


    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isBlank()) {
            return true;
        }
        return !usuarioRepository.existsByEmail(email);
    }
}
