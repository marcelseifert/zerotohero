/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Marcel
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessagePathParam {
    String value() default "";
}
