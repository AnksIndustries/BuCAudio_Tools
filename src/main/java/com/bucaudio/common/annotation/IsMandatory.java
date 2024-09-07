package com.bucaudio.common.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsMandatoryValidator.class)
public @interface IsMandatory {

    String message () default "Not a valid course";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
