/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
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
@Service(value = BaseService.class)//uses generic Service on top of MBean entity(Person in case)
public class SimpleMBean extends BaseMBean<Person> {

    @Override
    public void delete() {
        super.delete(); //To change body of generated methods, choose Tools | Templates.
    }

    //generic service injection is also supported
    //    @Inject
    //    public void setService(@Service BaseService<Person> service){
    //        super.setBaseService(service);
    //    }
    //    @Override
    //    public Person create() {
    //        return new Person();
    //    }
    
     
    
    
}
