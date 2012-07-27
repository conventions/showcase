/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.ConversationalMBean;
import org.conventionsframework.bean.modal.ModalObserver;
import org.conventionsframework.event.ModalCallback;
import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.showcase.util.ConstantUtils;
import org.conventionsframework.util.MessagesController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael M. Pestano Mar 19, 2011 2:16:11 PM
 */
@ConversationScoped
@Named(value="personConversationMBean")
public class PersonConversationMBean extends ConversationalMBean<Person> implements Serializable, ModalObserver {

    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * @param personService
     */
    @EJB //@Inject //glassfish and JBoss bug, the former works only with @EJB and the later with @Inject. They should work with both.
    public void setPersonService(PersonService personService) {
        super.setBaseService(personService);
    }

    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }

    /**
     * 
     * this method is called on saveButton is clicked 
     * you do not need to override it
     */
    @Override
    public void store() {

        super.store();
    }


    /**
     * removeFromList is called by removeButton with persistentRemove="false"
     */
    @Override
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
     * this method is called after 'newButton' is clicked  
     * you dont need to override this method
     */
    @Override
    public String afterPrepareInsert() {
        beginConversation();
        return "addUserConversation.faces?cid=" + conversation.getId() + "&amp;faces-redirect=true";
    }



    /**
     * this method is called after 'editButton' is clicked
     * you dont need to override this method
     */
    @Override
    public String afterPrepareUpdate() {
        beginConversation();
        return "addUserConversation.faces?cid=" + conversation.getId() + "&amp;faces-redirect=true";
    }

    /**
     * called when 'filterButton' is clicked
     * you dont need to override this method
     */
    @Override
    public void find() {
    }



    @Override
    public Person create() {
        Person p = new Person();
        p.setAge(25);
        return p;
    }

    public String back() {
        return goList();
    }

    public String backEdit() {
        return "addUserConversation.faces?cid=" + conversation.getId() + "&amp;faces-redirect=true";
    }

    public String associateFriends() {
        return "addFriendConversation.faces?cid=" + conversation.getId() + "&amp;faces-redirect=true";
    }

    @Override
   public void modalResponse(@Observes(notifyObserver= Reception.IF_EXISTS) ModalCallback callback) {
        
        /**
         * invokerName is used for identifying purposes as ModalCallback event
         * can be observed by various managed beans.
         * also receive= Reception.IF_EXISTS can do this job
         */
        if(callback.getInvokerName() != null && callback.getInvokerName().equals(ConstantUtils.Invoker.PERSON_CONVERSATION_BEAN)){
            if (getEntity().getFriends() == null) {
                getEntity().setFriends(new ArrayList<Person>());
            }
            Person[] selectedPerson = (Person[]) callback.getResult();
            for (Person person : selectedPerson) {
                if (!getEntity().hasFriend(person.getId())) {
                    getEntity().getFriends().add(getPersonService().load(person.getId()));
                }
            }
        }
    }
    
   public void initPersonSelectionModal() {
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("age", getEntity().getAge().toString());
        parameters.put("ignoreId", getEntity().getId());
        super.initModal(PersonSelectionModalMBean.MODAL_NAME, parameters);
    }
     
     public String goList(){
         beginConversation();
         return "personConversation.faces??cid=" + conversation.getId() + "&amp;faces-redirect=true";
     }
}