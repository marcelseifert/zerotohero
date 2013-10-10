/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;  
import java.util.logging.Level;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author Marcel
 */
public class JSONDesSer<T> implements Deserializer<T>, Serializer {

    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonFactory jf = new JsonFactory();

    private static Logger log = LoggerFactory.getLogger(JSONDesSer.class);
    
    @Override
    public T createFromString(String content, Class<T> classType) {
        try { 
            return mapper.readValue(content, classType);
        } catch (JsonMappingException | JsonParseException   ex ) {
            log.error("Fehler",ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JSONDesSer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public String createString(Object pojo) {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator jg = jf.createGenerator(sw);
                 jg.useDefaultPrettyPrinter();

            mapper.writeValue(jg, pojo);
            return sw.toString();
        }catch(IOException io) {
            log.error("Fehler",io);
        }
        return null;
    }
    
}
