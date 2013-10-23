/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.handler;

import org.marcel.web.zerotohero.message.Message;

/**
 *
 * @author Marcel
 */
public interface Commando {
    
    public void execute(Message m);
}
