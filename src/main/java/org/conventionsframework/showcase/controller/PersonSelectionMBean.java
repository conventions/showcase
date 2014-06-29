/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.util.MessagesController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Rafael M. Pestano Mar 19, 2011 2:16:11 PM
 */
@Named("personSelectionMBean")
@ViewAccessScoped
public class PersonSelectionMBean extends BaseMBean<Person>  implements Serializable {

    private List<Person> list;

    @Inject
    PersonService personService;


    @PostConstruct
    @Override
    public void init() {
        setBaseService(personService);
        super.init();
        Person p = new Person();
        p.setAge(100);
        this.setList(personService.crud().example(p).list());
    }


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