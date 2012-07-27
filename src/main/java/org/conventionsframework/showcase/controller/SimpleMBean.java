/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import javax.inject.Inject;
import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.showcase.model.Person;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.qualifier.Type;
import org.conventionsframework.service.BaseService;

/**
 *
 * @author rmpestano
 */

@Named("simpleMBean")
@ViewAccessScoped
@PersistentClass(Person.class)//managed bean entity
@Service(name=Service.STATEFUL,entity=Person.class)//same as commented setService method
public class SimpleMBean extends BaseMBean<Person> {
    
    
    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * or use @Service annotation to provide the name of an existing service @see PersonMBean
     * @param baseService
     */
//    @Inject 
//    public void setService(@Service(type= Type.STATEFUL,entity=Person.class) BaseService service){
//        super.setBaseService(service);
//    }

    
}
