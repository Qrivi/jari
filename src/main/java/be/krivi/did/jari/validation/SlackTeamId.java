package be.krivi.did.jari.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.FIELD, ElementType.PARAMETER } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = { SlackTeamIdValidator.class } )
public @interface SlackTeamId{

    boolean allowNull() default false;

    String message() default "{be.krivi.did.jari.validation.SlackTeamId.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}