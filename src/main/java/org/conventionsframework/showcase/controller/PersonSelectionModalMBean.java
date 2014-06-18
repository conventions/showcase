/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.WindowScoped;
import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.bean.ModalMBean;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import org.primefaces.event.CloseEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

 /**
 * 
 * @author rmpestano
 */
@Named(value = "personSelectionModalMBean")
@WindowScoped
public class PersonSelectionModalMBean extends ModalMBean<Person> implements Serializable {

    public PersonSelectionModalMBean() {
    }

    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
    @Inject
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }


    @Override
    public Object modalCallback() {
       return getPaginator().getSearchModel().getSelection();
    }

    /**
     * this event is fired by initModal method
     * @see PersonSelectionMBean#initPersonSelectionModal()
     * @see PersonMBean#initPersonSelectionModal()
     * @see BaseMBean#initModal()
     * @param modalInit 
     */
    @Override
    public void onOpen() {
           //parameter variable is populated via CDI event
           //and used to pass parameters from caller to modalMBeans
           //eg: open a modal with already filtered listOfValues
           if(getParameters() != null){
               getPaginator().getFilter().put("ignoreId", super.getParameters().get("ignoreId"));
           }
           
    }
  
    public void clearSelection(CloseEvent event){
        getPaginator().getSearchModel().setSelection(null);
    }
    
}