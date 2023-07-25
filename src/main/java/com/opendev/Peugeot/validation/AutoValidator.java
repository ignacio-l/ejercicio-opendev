package com.opendev.Peugeot.validation;

import com.opendev.Peugeot.model.Auto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AutoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clase) {
        return Auto.class.equals(clase);
    }


    @Override
    public void validate(Object target, Errors errors) {

        Auto auto = (Auto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "marca","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "modelo","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipo","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "puertas","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxVel","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "motor","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "torque","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hp","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio","No puede estar nulo o vacio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unidadesEnStock","No puede estar nulo o vacio");

    }
}