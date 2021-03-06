/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.producer.ResourceBundleProvider;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author rmpestano
 */
@Named
@ApplicationScoped
public class ApplicationInfoMBean implements Serializable{
    
    private Map<String,String> infoMap = new HashMap<String, String>();
    @Inject
    private ResourceBundleProvider resourceBundleProvider;
    private Date buildTime;
    
    @PostConstruct
    public void initialize(){
        
        String appProperties  = resourceBundleProvider.getCurrentBundle().getString("application.properties");
        appProperties = appProperties.substring(1, appProperties.indexOf("}"));
        String[] arrayProperties = appProperties.split("[\\s,]+");
        for (String prop : arrayProperties) {
             String[] keyValue = prop.split("=");
             if(keyValue.length == 2){
                infoMap.put(keyValue[0], keyValue[1]);
             }
        }
        
        buildTime = new Date();
    }

    public Map<String, String> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(Map<String, String> infoMap) {
        this.infoMap = infoMap;
    }

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    
}
