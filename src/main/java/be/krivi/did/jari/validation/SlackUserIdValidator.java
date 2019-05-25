package be.krivi.did.jari.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlackUserIdValidator implements ConstraintValidator<SlackUserId,String>{

    private boolean allowNull;

    @Override
    public void initialize( SlackUserId constraint ){
        this.allowNull = constraint.allowNull();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( value == null )
            return this.allowNull;
        return value.matches( "^U[A-Z0-9]{8}$" );
    }
}