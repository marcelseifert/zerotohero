/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero;

/**
 *
 * @author Marcel
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.jeromq.*;
import org.marcel.web.zerotohero.handler.MessageDefaultActivity;
import org.marcel.web.zerotohero.message.Message;
import org.marcel.web.zerotohero.handler.MessageHandler;
import org.marcel.web.zerotohero.handler.MessagePathParam;
import org.marcel.web.zerotohero.handler.ResourceHandler;
import org.marcel.web.zerotohero.model.Adresse;
import org.marcel.web.zerotohero.model.Anrede;
import org.marcel.web.zerotohero.model.Person;
import org.marcel.web.zerotohero.util.Deserializer;
import org.marcel.web.zerotohero.util.JSONDesSer;
import org.marcel.web.zerotohero.util.Serializer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Server {

    @Inject 
    private Instance<ResourceHandler> handlers;
 
    
    private Deserializer<Message> desMessage = new JSONDesSer<Message>();
    private Serializer serMessage = new JSONDesSer<Message>();

    private ConcurrentHashMap<String, Object> queue = new ConcurrentHashMap<String, Object>();

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public void run() throws Exception {

        ZMQ.Context context = ZMQ.context(1000);
        //  Socket to talk to clients
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        socket.bind("tcp://*:5555");
        registerHandler();

        while (!Thread.currentThread().isInterrupted()) {
            byte[] reply = socket.recv(0);  
            Message m = desMessage.createFromString( new String(reply,"UTF-8"), Message.class);
            m.setPayLoad( informHandler(m) );
            String request = serMessage.createString(m);
            socket.send(request.getBytes(), 0); 
        }
        socket.close();
        context.term();
    }

    private void registerHandler() {
         log.debug("registerHandler start "+handlers.iterator().hasNext());
        for (Object handler : handlers) {
            List<String> resourcePaths = buildResourcePaths(handler);
            for (String resourcePath : resourcePaths) {
                queue.put(resourcePath, handler);
                log.debug("registerHandler for Path "+resourcePath);
            }
        }
    }

    private List<String> buildResourcePaths(Object handler) {
        List<String> resourcePaths = new ArrayList<>();
        String resourcePath = getResourcePathFromAnnotation(handler);
        resourcePaths.add(resourcePath);
        Method[] m = handler.getClass().getMethods();
        for (Method method : m) {
            if (method.isAnnotationPresent(MessagePathParam.class)) {
                MessagePathParam mpp = method.getAnnotation(MessagePathParam.class);
                String subPath = mpp.value();
                if (!subPath.startsWith("/")) {
                    subPath = "/" + subPath;
                }
                if (subPath.contains("{")) {
                    subPath = subPath.substring(0,subPath.indexOf("{"));
                    if( subPath.length() == 1 ) subPath="";
                }
                if (subPath.indexOf("/", 1) > -1) {
                    resourcePaths.add(resourcePath + subPath.substring(0, subPath.indexOf("/", 1)));
                } else {
                    resourcePaths.add(resourcePath + subPath);
                }
            }
        }
        return resourcePaths;

    }

    private String informHandler(Message m) {
        String result = null;
        try { 
            Object handler = queue.get(m.getResourcePath());
            if( handler != null) { 
                Object payLoad = new JSONDesSer<>().createFromString(m.getPayLoad(), (Class<Object>) Class.forName(m.getTypeOfPayLoad()));
                result =  new JSONDesSer().createString(invokeMethod(m.getResourcePath(), handler, payLoad));
             }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    private Object invokeMethod(String resourcePathMessage, Object handler, Object payLoad) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] methods = handler.getClass().getMethods();
        Object result = null;
        String resourcePath = getResourcePathFromAnnotation(handler);
        String subResourcePath = resourcePathMessage.substring(
                resourcePath.length() - 1,
                resourcePathMessage.length());

        for (Method m : methods) {
            if (m.isAnnotationPresent(MessagePathParam.class)) {
                String pathParam = m.getAnnotation(MessagePathParam.class).value();
                if (pathParam.contains("{")) {
                    int index = pathParam.indexOf("{");
                    pathParam = pathParam.substring(pathParam.indexOf("{"), pathParam.length());
                    if (pathParam.equalsIgnoreCase(subResourcePath.substring(0, index))) {
                        int id = Integer.parseInt(
                                subResourcePath.substring(index, subResourcePath.length())
                        );
                        result = m.invoke(handler, id);
                        break;
                    }
                } else if (subResourcePath.equalsIgnoreCase(pathParam)) {
                    if (m.getParameterTypes().length == 1) {
                        result = m.invoke(handler, payLoad);

                    } else {
                        result = m.invoke(handler);
                    }
                    break;
                }

            } else if (m.isAnnotationPresent(MessageDefaultActivity.class)) {
                if (m.getParameterTypes().length == 1) {
                    result = m.invoke(handler, payLoad);
                } else {
                    result = m.invoke(handler);
                }
                break;
            }

        }
        return result;
    }

    private String getResourcePathFromAnnotation(Object handler) {
        if( handler.getClass().isAnnotationPresent(MessageHandler.class)) {
                return handler.getClass().getAnnotation(MessageHandler.class).resourcePath();
        }
        return null;
    }
}
