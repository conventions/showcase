/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.bean.StateMBean;
import org.conventionsframework.bean.state.CrudState;
import org.conventionsframework.qualifier.BeanState;
import org.conventionsframework.qualifier.BeanStates;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.ShowcaseState;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.showcase.util.ConstantUtils;
import org.conventionsframework.showcase.util.Pages;
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
@Named("trackablePersonMBean")
@BeanStates({
    //as some people asked me, translating:
    //if this managed bean is in "find state", a breadCrumb link with title "Search ManagedBean1" will be generated in the page(if historyStackLinks component is present),
    //also when you click the link it will execute an action with return = "Pages.History.LIST_PAGE+ConstantUtils.FACES_REDIRECT"
    @BeanState(beanState = ConstantUtils.State.FIND_STATE, outcome = Pages.History.LIST_PAGE + ConstantUtils.FACES_REDIRECT, value = "Search ManagedBean1"),
    @BeanState(beanState = ConstantUtils.State.INSERT_STATE, outcome = Pages.History.EDIT_PAGE + ConstantUtils.FACES_REDIRECT, value = "Create ManagedBean1",callback = "#{trackablePersonMBean.setUpdateState}"),
    @BeanState(beanState = ConstantUtils.State.UPDATE_STATE, outcome = Pages.History.EDIT_PAGE + ConstantUtils.FACES_REDIRECT, value = "Edit ManagedBean1"),
    @BeanState(beanState = ConstantUtils.State.FRIEND_STATE, outcome = Pages.History.FRIEND_PAGE + ConstantUtils.FACES_REDIRECT, value = "Friends of ManagedBean1")
})
@PersistentClass(Person.class)
public class TrackablePersonMBean extends StateMBean<Person> implements Serializable {

    @Inject
    PersonService personService;


    /**
     * this method is REQUIRED (or use the
     *
     * @Service annotation) to tell the framework how to 'crud' the managed
     * bean's entity
     */
    @Inject
    public void setPersonService() {
        super.setBaseService(personService);
    }

    public PersonService getPersonService() {
        return (PersonService)super.getBaseService();
    }


    public boolean isFriendState() {
        return ShowcaseState.isFriendState(getBeanState());
    }

    /**
     * this method is called after 'newButton' is clicked you dont need to
     * overhide this method
     */
    @Override
    public String afterPrepareInsert() {
        return Pages.History.EDIT_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    @Override
    public String afterPrepareUpdate() {
        return Pages.History.EDIT_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    public String associateFriends() {
        setBeanState(ShowcaseState.FRIEND);
        return Pages.History.FRIEND_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    @Override
    public void afterModalResponse() {

        if (getEntity().getFriends() == null) {
            getEntity().setFriends(new ArrayList<Person>());
        }
        List<Person> selectedPerson = (List<Person>) getModalResponse();
        for (Person person : selectedPerson) {
            if (!getEntity().hasFriend(person.getId())) {
                getEntity().getFriends().add((Person) getPersonService().crud().load(person.getId()));
            }
        }
    }

    public void initPersonSelectionModal() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", getEntity().getAge().toString());
        parameters.put("ignoreId", getEntity().getId());
        super.initModal(parameters);
    }

    public String go() {
        setBeanState(CrudState.FIND);
        return Pages.History.LIST_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    public void removeFromList() {
        if (getEntity().getFriends() == null) {
            return;
        }
        if (getEntity().hasFriend(getEntityAux().getId())) {
            getEntity().removeFriend(getEntityAux().getId());
            MessagesController.addInfo("Friend removed from list");
        }
    }
}
