package org.example.elasticsearch.validator;

import org.example.elasticsearch.metadata.PublicationYear;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

public class PublicationYearValidator implements ConstraintValidator<PublicationYear, Integer> {


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return !Year.of(value).isAfter(Year.now());
    }
}
