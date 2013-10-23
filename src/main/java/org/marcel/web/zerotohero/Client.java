package org.marcel.web.zerotohero;

/**
 * Hello world!
 *
 */

import java.io.UnsupportedEncodingException;
import org.jeromq.*;
import org.marcel.web.zerotohero.message.Message; 
import org.marcel.web.zerotohero.util.Deserializer;
import org.marcel.web.zerotohero.util.JSONDesSer;
import org.marcel.web.zerotohero.util.Serializer;

public class Client 
{
    public static void main( String[] args ) throws UnsupportedEncodingException
    {
        Client cl = new Client(); 
        
        Serializer serMessage = new JSONDesSer<Message>();
        Deserializer<Message> des =  new JSONDesSer<Message>();
        
        ZMQ.Context context = ZMQ.context(1);
        //  Socket to talk to server
        System.out.println("Connecting to hello world server");

        ZMQ.Socket socket = context.socket(ZMQ.REQ);
         
        socket.connect ("tcp://localhost:5555");

        for(int requestNbr = 0; requestNbr != 10; requestNbr++) {
            Message message = new Message();
            message.setPayLoad("Hello with No "+requestNbr);
            message.setTypeOfPayLoad("java.lang.String");
            message.setResourcePath("/hello");
          
            socket.send(   serMessage.createString(message).getBytes("UTF-8"), 0);

            byte[] reply = socket.recv(0);
            System.out.println("Received " + new String (reply) + " " + requestNbr);
            //Message p =  des.createFromString(new String (reply) , Message.class);
            //System.out.println("mappe back to Person-Anrede " + p.getPayLoad()+ " " + requestNbr); 
        }
        
        socket.close();
        context.term();
    }
}
