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

public class Server {
    
    public static void main(String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);
        //  Socket to talk to clients
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.bind ("tcp://*:5555");

        while (!Thread.currentThread ().isInterrupted ()) {
            byte[] reply = socket.recv(0);
            System.out.println("Received Hello");
            String request = "World" ;
            socket.send(request.getBytes (), 0);
            Thread.sleep(1000); //  Do some 'work'
        }
        socket.close();
        context.term();
    }
}
