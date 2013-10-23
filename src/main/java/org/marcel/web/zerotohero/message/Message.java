/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcel.web.zerotohero.message;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Marcel
 */
public class Message implements Serializable{

       
    private String resourcePath;
    
    private String payLoad;  
    
    private String typeOfPayLoad; 
     
    public Message() {}

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public String getTypeOfPayLoad() {
        return typeOfPayLoad;
    }

    public void setTypeOfPayLoad(String typeOfPayLoad) {
        this.typeOfPayLoad = typeOfPayLoad;
    }
     
    
}
