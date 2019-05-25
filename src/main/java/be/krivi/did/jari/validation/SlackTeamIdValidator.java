package be.krivi.did.jari.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlackTeamIdValidator implements ConstraintValidator<SlackTeamId, String>{

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        return value !=null && value.matches( "^T[A-Z0-9]{8}$" );
    }
}