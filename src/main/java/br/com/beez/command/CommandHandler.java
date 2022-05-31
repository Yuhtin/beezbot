package br.com.beez.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandHandler {

    String name();
    String description();

    /*
        Type [<argtype>arg]-Arg description for a not required arg
        Type <<argtype>arg>-Arg description for a required arg

        Arg types:
        # - Channel
        % - Role
        @ - Mention user
        ! - Integer
        Nothing - String
     */

    String[] args();

}