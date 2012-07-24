package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.StateMBean;
import org.conventionsframework.bean.state.CrudState;
import org.conventionsframework.bean.state.State;
import org.conventionsframework.qualifier.BeanState;
import org.conventionsframework.qualifier.BeanStates;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.qualifier.SecurityMethod;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.showcase.util.ConstantUtils;
import org.conventionsframework.showcase.util.Pages;
import org.conventionsframework.util.MessagesController;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;



/**
* bean description
*/
@Named("securityMBean")
@ViewAccessScoped
@BeanStates({
    @BeanState(beanState=ConstantUtils.State.FIND_STATE,page=Pages.Security.LIST_PAGE+ConstantUtils.FACES_REDIRECT,title="Search Person"),
    @BeanState(beanState=ConstantUtils.State.INSERT_STATE,page=Pages.Security.EDIT_PAGE+ConstantUtils.FACES_REDIRECT,title="Create Person"),
    @BeanState(beanState=ConstantUtils.State.UPDATE_STATE,page=Pages.Security.EDIT_PAGE+ConstantUtils.FACES_REDIRECT,title="Edit Person"),
    @BeanState(beanState="init",page=Pages.Security.HOME_PAGE+ConstantUtils.FACES_REDIRECT,title="Security Home"),
    @BeanState(beanState="secret",page=Pages.Security.SECRET_PAGE+ConstantUtils.FACES_REDIRECT,title="Secret Area")
})
@PersistentClass(value=Person.class)
public class SecurityMBean extends StateMBean<Person> implements Serializable{
    
    private String currentRole = "";

    public SecurityMBean() {
        this.clearProfile();
    }
    
    @Inject
    public void setPersonService(PersonService personService){
        super.setBaseService(personService);
    }
    
    public PersonService getPersonService(){
        return (PersonService)super.getBaseService();
    }
  
    /**
     *logic to perform on saveButton click
    */  
    @Override
    @SecurityMethod(rolesAllowed=ConstantUtils.Role.ROLE_ADMIN)
    public void store() {
        super.store();
    }    
   
    /**
     * logic to perform after addButton is clicked 
     * usually page to go
    */  
    @Override
    public String afterPrepareInsert() {
          return Pages.Security.EDIT_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    /**
     * logic to perform after editButton is clicked
     * usually page to go
    */ 
    @Override
    public String afterPrepareUpdate() {
        return this.afterPrepareInsert();//edit page and insert page are the same
    }  
    
    /**
     * called by addButton click
     */ 
    @Override
    @SecurityMethod(rolesAllowed=ConstantUtils.Role.ROLE_ADMIN) //security annotation, only roles allowed can perform this action
    public String prepareInsert() {
        return super.prepareInsert();
    }

    /**
     * called by editButton click
     */ 
    @Override
    @SecurityMethod(rolesAllowed=ConstantUtils.Role.ROLE_ADMIN) //security annotation, only roles allowed can perform this action
    public String prepareUpdate() {
        return super.prepareUpdate();
    }
    
     /**
     * called when 'filterButton' is clicked
     */
    @Override
    public void find() {
        
    }

    
    @SecurityMethod(rolesAllowed={ConstantUtils.Role.ROLE_ADMIN,ConstantUtils.Role.ROLE_GUEST} ,message="securityGoList")
    public String goList(){
        setBeanState(CrudState.FIND);
        return Pages.Security.LIST_PAGE + ConstantUtils.FACES_REDIRECT;
    }
    
    public String goSecurity(){
        setBeanState(SecurityState.INIT);
        currentRole = "";
        this.clearProfile();
        return Pages.Security.HOME_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    @SecurityMethod(rolesAllowed="secret")
    public String goSecretArea(){
        setBeanState(SecurityState.SECRET);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userRoles",new ArrayList<String>(){{add(currentRole);}});
        return Pages.Security.SECRET_PAGE + ConstantUtils.FACES_REDIRECT;
    }

    @Override
    @SecurityMethod(rolesAllowed=ConstantUtils.Role.ROLE_ADMIN)
    public void delete() {
        super.delete();
    }
    
    
 

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public void changeProfile(){
        MessagesController.addInfo("Role changed to: "+currentRole);
        if(currentRole != null && SecurityState.SECRET.getStateName().endsWith(currentRole)){
            setBeanState(SecurityState.SECRET);
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userRoles", new ArrayList<String>(){{add(currentRole);}});
    }
    
    public boolean isInitState(){
        return SecurityState.INIT.equals(getBeanState());
               
    }
    public boolean isSecretState(){
        return SecurityState.SECRET.equals(getBeanState());
               
    }

    private void clearProfile() {
           FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userRoles",null);

    }
    
}
/**
 * Custom state for security page
 * @author rmpestano
 */
   enum SecurityState implements State {

    INIT("init"),SECRET("secret");
    
    private final String stateName;

    SecurityState(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String getStateName() {
        return this.stateName;
    }
    
    @Produces
    @Named
    public State initState(){
        return SecurityState.INIT;
    }
    
    @Produces
    @Named
    public State secretState(){
        return SecurityState.SECRET;
    }

}
