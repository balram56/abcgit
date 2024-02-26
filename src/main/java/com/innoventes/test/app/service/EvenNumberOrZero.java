package com.innoventes.test.app.service;

import com.innoventes.test.app.service.impl.EvenNumberOrZeroValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EvenNumberOrZeroValidator.class)
public @interface EvenNumberOrZero {
    String message() default "Field must be an even number or zero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
