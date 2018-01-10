package fr.afpa.balthazar.logic.validators;

import fr.afpa.balthazar.logic.model.Auteur;
import fr.afpa.balthazar.logic.model.Collection;
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
public class collectionValidator {

    private Validator validator;


    public collectionValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    public Set<ConstraintViolation<Collection>> validateCollection(Collection collection) {
        Set<ConstraintViolation<Collection>> violations =  validator.validate(collection);
        return violations;
    }
}
