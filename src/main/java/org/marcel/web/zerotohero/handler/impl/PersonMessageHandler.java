/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.handler.impl;

import javax.enterprise.inject.Default;
import javax.inject.Named;
import org.marcel.web.zerotohero.handler.MessageHandler;
import org.marcel.web.zerotohero.handler.MessagePathParam;
import org.marcel.web.zerotohero.handler.ResourceHandler;
import org.marcel.web.zerotohero.model.Person;

/**
 *
 * @author Marcel
 */ 
@Default
@MessageHandler(resourcePath = "/person")
public class PersonMessageHandler implements ResourceHandler{
    
    @MessagePathParam("{id}")
    public Person getById(int id) {
        return null;
    }
}
