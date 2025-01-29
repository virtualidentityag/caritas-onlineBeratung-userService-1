package de.caritas.cob.userservice.api.adapters.web.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for the state property of the registration {@link UserDTO}. The
 * state is optional by default but could be mandatory for specific consulting types (e.q. U25)
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStateValidator.class)
public @interface ValidState {

  String message() default "{user.custom.state.invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
