/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.util;

/**
 *
 * @author Marcel
 */
interface Deserializer<T> {
    
    T createFromString(String content, Class<T> classType);
}
