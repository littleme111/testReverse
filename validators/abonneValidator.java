package fr.afpa.balthazar.logic.validators;

import fr.afpa.balthazar.logic.model.Abonne;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 * All Validators in package work the smae way.
 * Get Validator from factory.
 * Validate model based on annotations.
 * return Set of Constraint Violations
 */

@Component
public class abonneValidator {


    private Validator validator;


    public abonneValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    public Set<ConstraintViolation<Abonne>> validateAbonne(Abonne abonne) {
        Set<ConstraintViolation<Abonne>> violations =  validator.validate(abonne);
        return violations;
    }
}