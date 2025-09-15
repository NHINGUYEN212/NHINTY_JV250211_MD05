package ra.tsu_jv250211_md05_session02.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HandleUniqueTitle.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTitle {
    String message() default "title already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

