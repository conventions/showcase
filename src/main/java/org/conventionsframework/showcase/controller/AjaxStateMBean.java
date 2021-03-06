/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.bean.StateMBean;
import org.conventionsframework.bean.state.State;
import org.conventionsframework.qualifier.BeanState;
import org.conventionsframework.qualifier.BeanStates;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.ShowcaseState;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.showcase.util.ConstantUtils;
import org.conventionsframework.showcase.util.Pages;
import org.conventionsframework.util.MessagesController;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rmpestano
 */
@ViewAccessScoped
@Named
@PersistentClass(Person.class)
@BeanStates({
    //if this managed bean is in "find state", a breadCrumb link with title "Search Person" will be generated in the page(if stateItens component is present),
    //also when you click the link it will execute the callback "#{ajaxStateMBean.setFindState}" and will update ":historyForm:pageControl"
    @BeanState(beanState = ConstantUtils.State.FIND_STATE, value = "Search Person", callback = "#{ajaxStateMBean.setFindState}", update = ":historyForm:pageControl"),
    @BeanState(beanState = ConstantUtils.State.INSERT_STATE, value = "Create Person", callback = "#{ajaxStateMBean.setInsertState}", update = ":historyForm:pageControl"),
    @BeanState(beanState = ConstantUtils.State.UPDATE_STATE, value = "Edit Person", callback = "#{ajaxStateMBean.setUpdateState}", update = ":historyForm:pageControl"),
    @BeanState(beanState = ConstantUtils.State.FRIEND_STATE, value = "Manage Friends", callback = "#{ajaxStateMBean.setFriendState}", update = ":historyForm:pageControl"),
    @BeanState(beanState = "init", value = "Ajax StateMBean", callback = "#{ajaxStateMBean.setInitState}", update = ":historyForm:pageControl")
})
@Service(PersonService.class)
public class AjaxStateMBean extends StateMBean<Person> {


    public boolean isFriendState() {
        return ShowcaseState.isFriendState(getBeanState());
    }

    public boolean isInitState() {
        return HistoryState.INIT.equals(getBeanState());
    }

    public void setFriendState() {
        setBeanState(ShowcaseState.FRIEND);
    }

    public void setInitState() {
        setBeanState(HistoryState.INIT);
    }

    public String go() {
        setBeanState(HistoryState.INIT);
        return Pages.AjaxHistory.HOME + ConstantUtils.FACES_REDIRECT;
    }

    public void goList() {
        setFindState();
    }

    public void goFriend() {
        setBeanState(ShowcaseState.FRIEND);
    }

    @Override
    public void afterModalResponse() {
        if (getEntity().getFriends() == null) {
            getEntity().setFriends(new ArrayList<Person>());
        }
        List<Person> selectedPerson = (List<Person>) getModalResponse();
        for (Person person : selectedPerson) {
            if (!getEntity().hasFriend(person.getId())) {
                getEntity().getFriends().add((Person) getBaseService().crud().load(person.getId()));
            }
        }
    }

    public void initPersonSelectionModal() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("age", getEntity().getAge().toString());
        parameters.put("ignoreId", getEntity().getId());//doesnt show me in list of available frieds 
        super.initModal(parameters);
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

    /**
     * if saving from friend page just store the entity and keep in friendState
     * case super.store set state to update
     */
    @Override
    public void save() {
        if (isFriendState()) {
            getBaseService().store(getEntity());
            MessagesController.addInfo(getUpdateMessage());
        } else {
            super.save();
        }

    }

    /**
     * Custom state for ajax history stack
     *
     * @author rmpestano
     */
    enum HistoryState implements State {

        INIT("init");
        private final String stateName;

        HistoryState(String stateName) {
            this.stateName = stateName;
        }

        @Override
        public String getStateName() {
            return this.stateName;
        }
    }
}
