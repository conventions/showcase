/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.util.MessagesController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Rafael M. Pestano Apr 4, 2011 7:58:11 PM
 */
@Named("comboMBean")
@ViewAccessScoped
public class ComboMBean implements Serializable {
   
    private List<Person> personList;
    private List<Person> selectedPersonList;
    private Person selectedPerson;
    private Person selectedPerson2;
    private Person selectedPersonRadio;
    private int selectedItem;
    
    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    @Inject
    @Service
    BaseService<Person> service;

    @PostConstruct
    public void initList(){
        Person p = new Person(); 
        p.setName("1");
        p.setAge(1);
        personList = service.crud().maxResult(5).listByExample(p);
        selectedPerson2 = personList.get(2);
    }

    
    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Person getSelectedPersonRadio() {
        return selectedPersonRadio;
    }

    public void setSelectedPersonRadio(Person selectedPersonRadio) {
        this.selectedPersonRadio = selectedPersonRadio;
    }

    public List<Person> getSelectedPersonList() {
        return selectedPersonList;
    }

    public void setSelectedPersonList(List<Person> selectedPersonList) {
        this.selectedPersonList = selectedPersonList;
    }

    public Person getSelectedPerson2() {
        return selectedPerson2;
    }

    public void setSelectedPerson2(Person selectedPerson2) {
        this.selectedPerson2 = selectedPerson2;
    }
    
    
    
    
    public void listener(){
        if(selectedPerson != null){
            MessagesController.addInfo("OneMenu2 selected:"+selectedPerson.getName());
        }
    }
}
