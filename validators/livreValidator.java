package fr.afpa.balthazar.logic.validators;

import fr.afpa.balthazar.logic.model.Abonne;
import fr.afpa.balthazar.logic.model.Livre;
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
public class livreValidator {


    private Validator validator;


    public livreValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    public Set<ConstraintViolation<Livre>> validateLivre(Livre livre) {
        Set<ConstraintViolation<Livre>> violations =  validator.validate(livre);
        return violations;
    }
}
