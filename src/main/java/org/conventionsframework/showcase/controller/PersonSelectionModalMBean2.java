/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.ModalMBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.conventionsframework.showcase.model.Person;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.qualifier.Type;
import org.conventionsframework.service.BaseService;

 /**
 * 
 * @author rmpestano
 */
@WindowScoped
@Named(value = "personSelectionModalMBean2")
public class PersonSelectionModalMBean2 extends ModalMBean<Person> implements Serializable {


    private List<Person> selectedPeople;
    private List<Person> list;
    public static final String MODAL_NAME = "personSelectionModal2";
    
    public PersonSelectionModalMBean2() {
    }

    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
    @Inject
    public void setPersonService(@Service(type=Type.STATEFUL,entity=Person.class)BaseService personService) {
        super.setBaseService(personService);
    }

    public BaseService getPersonService(){
        return super.getBaseService();
    }
    
    @PostConstruct
    @Override
    public void init(){
        Person p = new Person();
        p.setAge(20);
        list = getPersonService().findByExample(p);
    }

    public List<Person> getSelectedPeople() {
        return selectedPeople;
    }

    public void setSelectedPeople(List<Person> selectedPeople) {
        this.selectedPeople = selectedPeople;
    }


    
    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }

    @Override
    public Object modalCallback(){
        return selectedPeople;
    }

}