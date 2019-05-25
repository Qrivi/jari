package be.krivi.did.jari.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlackUserIdValidator implements ConstraintValidator<SlackUserId, String>{

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        return value !=null && value.matches( "^U[A-Z0-9]{8}$" );
    }
}