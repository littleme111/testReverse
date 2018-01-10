package fr.afpa.balthazar.logic.validators;

import fr.afpa.balthazar.logic.model.Collection;
import fr.afpa.balthazar.logic.model.Exemplaire;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * See abonneValidator for comment
 */
@Component
public class exemplaireValidator {
    private Validator validator;


    public exemplaireValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    public Set<ConstraintViolation<Exemplaire>> validateExemplaire(Exemplaire exemplaire) {
        Set<ConstraintViolation<Exemplaire>> violations =  validator.validate(exemplaire);
        return violations;
    }
}
