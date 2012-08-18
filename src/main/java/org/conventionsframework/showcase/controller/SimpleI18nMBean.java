/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.event.LocaleChangeEvent;
import org.conventionsframework.model.AbstractBaseEntity;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.util.VersionUtils;

/**
 *
 * @author rmpestano Oct 2, 2011 11:48:37 AM
 */
@Named
@ViewAccessScoped
public class SimpleI18nMBean extends BaseMBean<AbstractBaseEntity> implements Serializable{

    private String selectedLocale;

    @PostConstruct
    public void initLocale () {
        selectedLocale = getResourceBundleProvider().getCurrentLocale();
    }
    
    
    @Inject 
    private Event<LocaleChangeEvent> localeChangeEvent;
    
    public String getSimpleHello(){
        return getResourceBundle().getString("simpleHello");
    }
    
    public String getParametrizedHello(){
         
        return getResourceBundle().getString("parametrizedHello","Conventions ",VersionUtils.INSTANCE.getCoreVersion().getVersion());
    }


    public String getSelectedLocale() {
        return selectedLocale;
    }

    public void setSelectedLocale(String selectedLocale) {
        this.selectedLocale = selectedLocale;
    }

    public void changeLocale(){
        if(selectedLocale != null && !"".endsWith(selectedLocale)){
            localeChangeEvent.fire(new LocaleChangeEvent(selectedLocale));
//   OR     getResourceBundleProvider().setCurrentLocale(selectedLocale);
            
        }
    }
    
}
