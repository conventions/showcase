/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowScoped;
import org.conventionsframework.bean.ModalMBean;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

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
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }
    
    @PostConstruct
    @Override
    public void init(){
        Person p = new Person();
        p.setAge(20);
        list = getPersonService().getDao().findByExample(p);
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