/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.qualifier.PropertyFile;
import org.conventionsframework.qualifier.PropertyKey;
import org.conventionsframework.util.MessagesController;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author Rafaem M. Pestano - Jul 8, 2012 3:36:45 PM
 */
@Named
@ViewAccessScoped
public class PropertiesMBean implements Serializable{

    @Inject @PropertyKey(key="key1",file="/org/conventionsframework/showcase/showcase.properties")
    private String key;
    
    @Inject @PropertyKey(key="key1",file="/otherProps.properties")
    private String otherKey;
    
    //default .properties file can be configured
    //via 'DEFAULT_PROPERTIES_PATH' contextParam in web.xml
    //so you dont net to specify the file everytime
    @Inject @PropertyKey(key="key2")
    private String key2;
    
    @Inject @PropertyKey(key="key2",file="/otherProps.properties")
    private String otherKey2;
    
    //Injection of properties files is also allowed
    @Inject @PropertyFile(file="/org/conventionsframework/showcase/showcase.properties")
    private Properties propertyFile;
    
    private String keyTyped;

 
    public void keyFromInjectedProperties(){
        if(keyTyped == null){//fixme 
            return;
        }
        String value = propertyFile.getProperty(keyTyped.trim());   
        if(value != null){
            MessagesController.addInfo("Value of "+keyTyped + " = " +value);
        }
        else{
            MessagesController.addError("Key:'"+keyTyped + "' not found in <span style='text-decoration:underline'>showcase.properties</span>");
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOtherKey() {
        return otherKey;
    }

    public void setOtherKey(String otherKey) {
        this.otherKey = otherKey;
    }

    public String getOtherKey2() {
        return otherKey2;
    }

    public void setOtherKey2(String otherKey2) {
        this.otherKey2 = otherKey2;
    }

    public String getKey2() {
        return key2;
    }

    public String getKeyTyped() {
        return keyTyped;
    }

    public void setKeyTyped(String keyTyped) {
        this.keyTyped = keyTyped;
    }
    
}
