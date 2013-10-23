/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author Marcel
 */
public class Startup {

    public static void main(String args[]) {
        Weld weld = new Weld();

        WeldContainer container = weld.initialize();
        Server s = container.instance().select(Server.class).get();
        try {
            s.run();
        } catch (Exception ex) {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }
        weld.shutdown();

    }

}
