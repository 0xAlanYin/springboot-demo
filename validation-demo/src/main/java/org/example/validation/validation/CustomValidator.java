package org.example.validation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomValidation, String> {

  @Override
  public void initialize(CustomValidation constraintAnnotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return null != value && value.startsWith("ABC");
  }
}
