/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;

import javax.inject.Named;

/**
 *
 * @author rmpestano
 */

@Named("simpleMBean")
@ViewAccessScoped
@PersistentClass(Person.class)//managed bean entity, you can use create() method instead
@Service(value = BaseService.class, entity = Person.class)//same as commented setService()
/**
 * as BaseService is generic you have to pass 'entity' attribute
 * if you use an existent service you don't need entity attr
 * eg; @Service(PersonService.class)
 */
public class SimpleMBean extends BaseMBean<Person> {

    @Override
    public void delete() {
        super.delete(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * or use @Service annotation passing the type of an existent service
     * @param baseService
     */
    //    @Inject
    //    public void setService(@Service BaseService<Person,Long> service){
    //        super.setBaseService(service);
    //    }
    //    @Override
    //    public Person create() {
    //        return new Person();
    //    }
    
     
    
    
}
