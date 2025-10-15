package pe.jsaire.tiendaapp.utils.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistByCategoriaValidation.class)
public @interface isExistsByCategoria {

    String message() default "El nombre de categoria ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
