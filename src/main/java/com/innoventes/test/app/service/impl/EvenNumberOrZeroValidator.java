package com.innoventes.test.app.service.impl;

import com.innoventes.test.app.service.EvenNumberOrZero;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EvenNumberOrZeroValidator implements ConstraintValidator<EvenNumberOrZero, Number> {
    @Override
    public void initialize(EvenNumberOrZero constraintAnnotation) {
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }
        return value.longValue() == 0 || value.longValue() % 2 == 0;
    }
}

