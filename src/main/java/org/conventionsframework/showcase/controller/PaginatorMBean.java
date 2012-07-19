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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.showcase.model.PhoneType;

/**
 * 
 * @author Rafael M. Pestano
 * @Date Jun 18, 2012
 */
@Named
@ViewAccessScoped
public class PaginatorMBean implements Serializable {

//    injection of paginator is also supported, you can inform the service at injection point
//    so you dont need to instantiate the Paginator and pass the service in the contructor like in initPaginator method    
//    @Inject @Service(name="personService") //uses the PersonServiceImpl as service, just uncomment 
//    @Inject @Service(name="statelessHibernateService",entity=Person.class) //also works, uses the BaseService as service, just uncomment 
    private Paginator paginator;
    
    @Inject
    @Service(name = "advancedFilterService")
    private Paginator advancedPaginator;
    
    @Inject
    @StatelessService(entity = Person.class)
    private BaseService baseService;

    public void initPaginator() {
        // paginator needs a service to access DB and perform
        // true pagination, sort and filtering over a collection
        paginator = new Paginator(baseService);
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public Paginator getAdvancedPaginator() {
        return advancedPaginator;
    }

    public List<PhoneType> getPhoneTypes() {
        return new ArrayList<PhoneType>(Arrays.asList(PhoneType.values()));
    }

    public void advancedSearch() {
        List<String> numbersList = new ArrayList<String>();
        numbersList.add("11111111");
        numbersList.add("22222222");
        numbersList.add("33333333");
        //add parammeters to the filter so service layer can get them in configFindPaginated
        advancedPaginator.getFilter().put("numberList", numbersList);
        advancedPaginator.getFilter().put("activateBetween", true);
    }
}
