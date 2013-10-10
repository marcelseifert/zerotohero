package org.marcel.web.zerotohero;

/**
 * Hello world!
 *
 */

import org.jeromq.*;
import org.marcel.web.zerotohero.model.Person;
import org.marcel.web.zerotohero.util.Deserializer;
import org.marcel.web.zerotohero.util.JSONDesSer;

public class Client 
{
    public static void main( String[] args )
    {
        Client cl = new Client();
        Deserializer<Person> des = new JSONDesSer<Person>();
        
        ZMQ.Context context = ZMQ.context(1);
        //  Socket to talk to server
        System.out.println("Connecting to hello world server");

        ZMQ.Socket socket = context.socket(ZMQ.REQ);
         
        socket.connect ("tcp://localhost:5555");

        for(int requestNbr = 0; requestNbr != 10; requestNbr++) {
            String request = "Hello" ;
            System.out.println("Sending Hello " + requestNbr );
            socket.send(request.getBytes (), 0);

            byte[] reply = socket.recv(0);
            System.out.println("Received " + new String (reply) + " " + requestNbr);
            Person p =  des.createFromString(new String (reply) , Person.class);
             System.out.println("mappe back to Person-Anrede " + p.getAnrede() + " " + requestNbr);
            System.out.println("mappe back to Person-PLZ " + p.getAdresse().getPlz() + " " + requestNbr);
        }
        
        socket.close();
        context.term();
    }
}
