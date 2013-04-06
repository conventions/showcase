/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.ModalMBean;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;
import org.conventionsframework.bean.BaseMBean;
import org.primefaces.event.CloseEvent;

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
    @Inject //@Inject //glassfish and JBoss bug, the former works only with @EJB and the later with @Inject. They should work with both.
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }


    @Override
    public Object modalCallback() {
       return getPaginator().getSelection();
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
        getPaginator().setSelection(null);
    }
    
}