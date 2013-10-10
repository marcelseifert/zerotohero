/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.message;

import java.util.HashMap;

/**
 *
 * @author Marcel
 */
public class Message {

    private HashMap<String,String> metaKeys = new HashMap<String,String>();
    
    private String payLoadType;
    private String payLoad; 
    
    public void addMetaKey(String key, String value) {
        metaKeys.put(key, value);
    }
    
    public String getMetaValue(String key) {
        return metaKeys.get(key);
    }
}
