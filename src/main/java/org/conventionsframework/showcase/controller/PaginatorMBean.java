/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.qualifier.StatelessService;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.util.Paginator;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano
 */
@Named
@ViewAccessScoped
public class PaginatorMBean implements Serializable{
    
//    injection of paginator is also supported, you can inform the service at injection point
//    so you dont need to instantiate the Paginator and pass the service in the contructor like in initPaginator method    
//    @Inject @Service(name="personService") //uses the PersonServiceImpl as service, just uncomment 
//    @Inject @Service(name="statelessHibernateService",entity=Person.class) //also works, uses the BaseService as service, just uncomment 
    private Paginator paginator;

    
    @Inject @StatelessService(entity=Person.class)
    private BaseService baseService;
    
    
    public void initPaginator(){
        // paginator need a service to access DB and perform
        // true pagination, sort and filtering over a collection
        paginator = new Paginator(baseService);
    }
    

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public Paginator getPaginator() {
        return paginator;
    }
    
}
