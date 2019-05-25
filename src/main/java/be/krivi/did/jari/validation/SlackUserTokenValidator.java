package be.krivi.did.jari.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlackUserTokenValidator implements ConstraintValidator<SlackUserToken, String>{

    private boolean optional;

    @Override
    public void initialize( SlackUserToken constraint ){
        this.optional = constraint.optional();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if(value == null)
            return this.optional;
        return value.matches( "^xoxp-[0-9]{11}-[0-9]{11}-[0-9]{12}-\\w{32}$" );
    }
}