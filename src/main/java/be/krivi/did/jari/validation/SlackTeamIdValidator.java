package be.krivi.did.jari.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SlackTeamIdValidator implements ConstraintValidator<SlackTeamId,String>{

    private boolean allowNull;

    @Override
    public void initialize( SlackTeamId constraint ){
        this.allowNull = constraint.allowNull();
    }

    @Override
    public boolean isValid( String value, ConstraintValidatorContext context ){
        if( value == null || value.equals( "" ) )
            return this.allowNull;
        return value.matches( "^T[A-Z0-9]{8}$" );
    }
}