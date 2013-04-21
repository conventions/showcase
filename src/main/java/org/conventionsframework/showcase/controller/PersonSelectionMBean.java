/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.util.MessagesController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.qualifier.Type;
import org.conventionsframework.service.BaseService;

/**
 *
 * @author Rafael M. Pestano Mar 19, 2011 2:16:11 PM
 */
@Named("personSelectionMBean")
@ViewAccessScoped
public class PersonSelectionMBean extends BaseMBean<Person>  implements Serializable {

    private List<Person> list;

    public PersonSelectionMBean() {
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        Person p = new Person();
        p.setAge(100);
        this.setList(getPersonService().findByExample(p));
    }

    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed
     * bean's entity
     *
     * @param personService
     */
    @Inject
    public void setPersonService(@Service(type = Type.STATEFUL, entity = Person.class) BaseService personService) {
        super.setBaseService(personService);
    }

    public BaseService getPersonService() {
        return (BaseService) super.getBaseService();
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
     * modalResponse is populated via CDI event so in 
     * afterModalResponse you can access modalResponse value
     * 
     * 
     * @see PersonSelectionModalMBean#modalCallback()
     */
    @Override
    public void afterModalResponse() {
        //note that modalResponse is an object and must be casted accordingly
        // your bean can get response from multiple modals so you may need instanceof 
        
        List<Person> selectedPerson = (List<Person>) getModalResponse();
        for (Person person : selectedPerson) {
            if (getList() != null && !getList().contains(person)) {
                getList().add(person);
            }
        }
    }

    /**
     * invokes beforeOpen modal event
     *
     * @see
     * PersonSelectionModalMBean#beforeOpen(org.conventionsframework.event.ModalInitialization)
     */
    public void initPersonSelectionModal() {
        getPaginator().getFilter().put("age", "1");
        /**
         * init modal is used to pass parameters to modals before it opens
         */
        super.initModal(getPaginator().getFilter());
    }
}