/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.bean.BaseMBean;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.showcase.model.Person;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano
 */

@Named("simpleMBean")
@ViewAccessScoped
@PersistentClass(Person.class)//managed bean entity, you can use create() method instead
@Service(name=Service.STATEFUL,entity=Person.class)//same as commented setService() method
//@Service(type = Type.STATEFUL,entity = Person.class)//also same as commented setService() method
//both @Service will Inject a generic service with an extended PersistenceContext to CRUD Person entity
//@Service(PersonService.class) //inject service by type, in this case you dont need entity attribute 
//cause PersonService is not generic, it is specialized in Person entity 
public class SimpleMBean extends BaseMBean<Person> {

    
     /**
     * this method is REQUIRED to tell the framework how to 'crud' the managed bean's entity
     * or use @Service annotation to provide the name,type or value of an existing service 
     * @param baseService
     */
//    @Inject 
//    public void setService(@Service(type= Type.STATEFUL,entity=Person.class) BaseService service){
//        super.setBaseService(service);
//    }

    
//    @Override
//    public Person create() {
//        return new Person();
//    }
    
}
