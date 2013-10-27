package org.marcel.web.zerotohero;

/**
 * Hello world!
 *
 */
import java.io.UnsupportedEncodingException;
import java.net.URL;
import org.jeromq.*;
import org.marcel.web.zerotohero.message.Message;
import org.marcel.web.zerotohero.util.Deserializer;
import org.marcel.web.zerotohero.util.JSONDesSer;
import org.marcel.web.zerotohero.util.Serializer;
import org.slf4j.LoggerFactory;

public class ClientProxy<T> {

    private final Serializer serMessage = new JSONDesSer<>();
    private final Deserializer<Message> desMessage = new JSONDesSer<>();
    private final String host;
    private final String resourcePath;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Server.class);

    public ClientProxy(String url) {
          int index = url.indexOf("/",6);
          host = url.substring(0,index);
          resourcePath = url.substring(index,url.length());
    }

    public T call(Object payload) {

        ZMQ.Context context = ZMQ.context(1);

        ZMQ.Socket socket = context.socket(ZMQ.REQ);

        socket.connect(host);

        Message message = new Message();
        message.setPayLoad(serMessage.createString(payload));
        if (payload != null) {
            message.setTypeOfPayLoad(payload.getClass().getName());
        }
        message.setResourcePath(resourcePath);
        try {
            socket.send(serMessage.createString(message).getBytes("UTF-8"), 0);
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getLocalizedMessage());
        }

        byte[] reply = socket.recv(0);
        Message m = desMessage.createFromString(new String(reply), Message.class);

        socket.close();
        context.term();
        try {
            return (T) new JSONDesSer<>().createFromString(m.getPayLoad(), (Class<Object>) Class.forName(m.getTypeOfPayLoad()));
        } catch (ClassNotFoundException ex) {
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }
 
}
