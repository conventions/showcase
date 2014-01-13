
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.bean.state.CrudState;
import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.ShowcaseState;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.util.MessagesController;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael M. Pestano Mar 19, 2011 2:16:11 PM
 */
@ViewAccessScoped
@Named("personMBean")
public class PersonMBean extends BaseMBean<Person> implements Serializable {

    /**
     * this method is REQUIRED (or use the
     *
     * @Service annotation) to tell the framework how to 'crud' the managed
     * bean's entity
     * @param personService
     */
    @Inject
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService() {
        return (PersonService) super.getBaseService();
    }

    @Override
    public Person create() {
        Person p = new Person();
        /* create person age starts with 25 by default */
        p.setAge(25);
        return p;
    }

    /**
     * called by saveButton
     */
    @Override
    public void save() {
        super.save();
    }

    public boolean isFriendState() {
        return ShowcaseState.FRIEND.equals(getBeanState());
    }

    /**
     * called after 'addButton' is clicked to decide the navigation
     */
    @Override
    public String afterPrepareInsert() {
        return null;
    }

    /**
     * called after 'editButton' is clicked to decide the navigation
     */
    @Override
    public String afterPrepareUpdate() {
        return null;
    }

    /**
     * called when 'filterButton' is clicked
     */
    @Override
    public void find() {
    }

    public void associateFriends() {
        setBeanState(ShowcaseState.FRIEND);
    }

    /**
     * ModalCallback event is fired by modal popup and is observed by
     * ModalObserver ManagedBeans to retrieve data from popup(acts like lov
     * pattern)
     *
     */
    @Override
    public void afterModalResponse() {
            if (getEntity().getFriends() == null) {
                getEntity().setFriends(new ArrayList<Person>());
            }
            //modalResponse in populated through CDI event and afterModalResponse
            // is called when a modal fires a modalCallbackEvent @see ModalMBean#invokeModalCallback()
            // invokeModalCallback is fired by modalButton component and is used to retrieve listOfValues
            List<Person> selectedPerson = (List<Person>) getModalResponse();
            //as you can see modalResponse is an object and should be cast accoardingly
            //also you may need instaceof cause this bean can receive events from multiple modal
            //which can return a variety of responses
            for (Person person : selectedPerson) {
                if (!getEntity().hasFriend(person.getId())) {
                    getEntity().getFriends().add(getPersonService().getDao().load(person.getId()));
                }
            }
    }

    public void initPersonSelectionModal() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", getEntity().getAge().toString());
        parameters.put("ignoreId", getEntity().getId());
        super.initModal(parameters);
    }

    public void backEdit() {
        setBeanState(CrudState.UPDATE);
    }

    public void back() {
        setBeanState(CrudState.FIND);
    }

    public void saveAndRollback() {
        getPersonService().setRollbackTest(true);
        getPersonService().store(getEntity());
    }

    public void removeSelected() {
        if (getEntityAuxList() != null && getEntityAuxList().length > 0) {
            for (Person p : getEntityAuxList()) {
                getEntity().removeFriend(p.getId());
            }
            MessagesController.addInfo("Friends removed from list");
            throw new BusinessException("No friends selected", "personCrudForm:friendsTable:bt-rm-all");
        }
        setEntityAuxList(null);
    }

    public void removeFromList() {
        if (getEntity().getFriends() == null) {
            return;
        }
        getEntity().removeFriend(getEntityAux().getId());
        MessagesController.addInfo("Friend removed from list");
    }
}
