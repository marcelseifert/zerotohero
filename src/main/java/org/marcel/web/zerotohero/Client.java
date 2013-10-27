package org.marcel.web.zerotohero;

/**
 * Hello world!
 *
 */

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class Client 
{
    public static void main( String[] args ) throws UnsupportedEncodingException, MalformedURLException
    {
        ClientProxy<String> cl = new  ClientProxy<>( "tcp://localhost:5555/hello"); 
          
        for(int requestNbr = 0; requestNbr != 200000; requestNbr++) {
            
            String result = cl.call("Hello with No "+requestNbr);
             System.out.println("result is "+result); 
        }
        
    }
}
