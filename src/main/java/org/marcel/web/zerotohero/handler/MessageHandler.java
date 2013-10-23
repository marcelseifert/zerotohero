/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.handler;
 
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*; 
import javax.inject.Qualifier;

/**
 *
 * @author Marcel
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER}) 
public @interface MessageHandler {
    String resourcePath() default  "";
}
