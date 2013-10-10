/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero;

/**
 *
 * @author Marcel
 */

import org.jeromq.*;
import org.marcel.web.zerotohero.model.Adresse;
import org.marcel.web.zerotohero.model.Anrede;
import org.marcel.web.zerotohero.model.Person;
import org.marcel.web.zerotohero.util.JSONDesSer;
import org.marcel.web.zerotohero.util.Serializer;

public class Server {
    
    public static void main(String[] args) throws Exception {
        Server s = new Server();
        Serializer  ser = new JSONDesSer<Person>();
        
        ZMQ.Context context = ZMQ.context(1);
        //  Socket to talk to clients
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.bind ("tcp://*:5555");

        while (!Thread.currentThread ().isInterrupted ()) {
            byte[] reply = socket.recv(0);
            System.out.println("Received Hello");
            String request = ser.createString(s.createMockPerson());
            socket.send(request.getBytes (), 0);
            Thread.sleep(1000); //  Do some 'work'
        }
        socket.close();
        context.term();
    }
    
    private Person createMockPerson() {
        Adresse ad = new Adresse("Musterallee 1",12345,"Musterhausen");
        Person p = new Person("Max","Mustermann", Anrede.HERR,ad);
        return p;
    }
}
