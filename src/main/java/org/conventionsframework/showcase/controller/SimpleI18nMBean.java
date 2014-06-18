/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.event.LocaleChangeEvent;
import org.conventionsframework.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @author rmpestano Oct 2, 2011 11:48:37 AM
 */
@Named
@ViewAccessScoped
public class SimpleI18nMBean implements Serializable{

    private String selectedLocale;

    @Inject
    @Named("conventionsVersion")
    String conventionsVersion;

    @Inject
    ResourceBundle bundle;

    @PostConstruct
    public void initLocale () {
        selectedLocale = Locale.getDefault().getLanguage();
    }
    
    
    @Inject 
    private Event<LocaleChangeEvent> localeChangeEvent;
    
    public String getSimpleHello(){
        return bundle.getString("simpleHello");
    }
    
    public String getParametrizedHello(){
         
        return bundle.getString("parametrizedHello", "Conventions ", conventionsVersion);
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
