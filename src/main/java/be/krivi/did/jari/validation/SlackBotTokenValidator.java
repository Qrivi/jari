package be.krivi.did.jari.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlackBotTokenValidator implements ConstraintValidator<SlackBotToken, String>{

    private boolean optional;

    @Override
    public void initialize( SlackBotToken constraint ){
        this.optional = constraint.optional();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if(value == null)
            return this.optional;
        return value.matches( "^xoxb-[0-9]{11}-[0-9]{12}-\\w{24}$" );
    }
}