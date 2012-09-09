/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.util.MessagesController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.qualifier.ConventionsEntityManager;
import org.conventionsframework.qualifier.Query;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.qualifier.Type;
import org.conventionsframework.showcase.model.Phone;
import org.conventionsframework.showcase.util.QueryUtils;

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
    private Person selectedPersonRadio;
    private int selectedItem;

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    @Inject
    public void queryProviderTest(QueryUtils queryUtils){
        List<Person> l1 = queryUtils.findPersonByOne();
        List<Person> l2 = queryUtils.findAllPerson();
        List<Phone> l3 = queryUtils.findPhones();
        System.out.println(l1.size());
        System.out.println(l2.size());
        System.out.println(l3.size());
        queryUtils.findPersonByAge();
    }
    
    
    @Inject
    public void initList(@Service(type= Type.CUSTOM,entity=Person.class) BaseService service){
        Person p = new Person(); 
        p.setName("1");
        p.setAge(1);
        personList = service.findByExample(p,5);
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
    
    
    public void listener(){
        if(selectedPerson != null){
            MessagesController.addInfo("OneMenu2 selected:"+selectedPerson.getName());
        }
    }
}
