package de.caritas.cob.userservice.api.adapters.web.dto.validation;

import de.caritas.cob.userservice.api.adapters.web.dto.UserDTO;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for the age property of the registration {@link UserDTO}. The age is
 * optional by default but could be mandatory for specific consulting types (e.q. U25)
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAgeValidator.class)
public @interface ValidAge {

  String message() default "{user.custom.age.invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
