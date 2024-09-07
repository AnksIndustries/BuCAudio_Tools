package com.bucaudio.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMandatoryValidator implements ConstraintValidator<IsMandatory, String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        return null != str && !str.isEmpty();
    }
}
