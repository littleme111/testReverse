package fr.afpa.balthazar.logic.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class CustomConstraintViolation extends ConstraintViolationException {


    public CustomConstraintViolation(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
        this.getSpecificMessage();
    }

    public CustomConstraintViolation(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }

    public void getSpecificMessage(){
        Set<ConstraintViolation<?>> s = getConstraintViolations();
        for(ConstraintViolation<?> a: s){
            System.out.println(a.getPropertyPath());
            System.out.println(a.getMessage());
        }
    }

}
