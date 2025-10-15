package pe.jsaire.tiendaapp.utils.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByEmailValidation.class)
public @interface isExistsByEmail {

    String message() default "Este email ya existe en la base de datos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
