/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.bean.modal.ModalObserver;
import org.conventionsframework.event.ModalCallback;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.util.MessagesController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.paginator.Paginator;

/**
 *
 * @author Rafael M. Pestano Mar 19, 2011 2:16:11 PM
 */
@Named("personSelectionMBean")
@ViewAccessScoped
public class PersonSelectionMBean extends BaseMBean<Person>
        implements Serializable, ModalObserver {

    private List<Person> list; 
    
    public PersonSelectionMBean() {
    }

    @PostConstruct
    @Override
    public void init() {
        Person p = new Person();
        p.setAge(100);
        setPaginator(new Paginator());
        this.setList(getPersonService().findByExample(p));
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

    /**
     * removeFromList is called by removeButton with persistentRemove="false"
     */
    @Override
    public void removeFromList() {
        if (getList().contains(getEntityAux())) {
            getList().remove(getEntityAux());
            MessagesController.addInfo("Person removed from list");
        }
    }

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }


    /**
     * the ModalCallback event if fired by modalButton 
     * and carries the value returned by modalCallback 
     * method implemented by AbstractModalMBean s 
     * @see PersonSelectionModalMBean#modalCallback()  
     * @param callback 
     */
    @Override
    public void modalResponse(@Observes(notifyObserver= Reception.IF_EXISTS) ModalCallback callback){
            Person[] selectedPerson = (Person[]) callback.getResult();
            for (Person person : selectedPerson) {
                if (getList() != null && !getList().contains(person)) {
                    getList().add(person);
                }
            }
    }
    
    /**
     * invokes beforeOpen modal event
     * @see PersonSelectionModalMBean#beforeOpen(org.conventionsframework.event.ModalInitialization) 
     */
    public void initPersonSelectionModal() {
        getPaginator().getFilter().put("age", "1"); 
        /**
         * init modal is used to pass parameters to modals
         * before it opens
         */
        super.initModal(PersonSelectionModalMBean.MODAL_NAME, getPaginator().getFilter());
    }
    
}