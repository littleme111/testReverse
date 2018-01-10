package fr.afpa.balthazar.logic.validators;

import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Auteur;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class auteurValidator {

    private Validator validator;


    public auteurValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    public Set<ConstraintViolation<Auteur>> validateAuteur(Auteur auteur) {
        Set<ConstraintViolation<Auteur>> violations =  validator.validate(auteur);
        return violations;
    }
}
