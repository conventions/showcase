/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.conventionsframework.paginator.Paginator;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.PhoneType;
import org.conventionsframework.showcase.service.AdvancedFilterService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Rafael M. Pestano
 * @Date Jun 18, 2012
 */
@Named
@ViewAccessScoped
public class PaginatorMBean implements Serializable {

    private Paginator<Person> paginator;
    
    @Inject
    @Service(AdvancedFilterService.class)
    private Paginator<Person>advancedPaginator;
    
    @Inject
    @Service
    private BaseService<Person> baseService;

    /**
     * called by load data button
     */
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
        advancedPaginator.getSearchModel().addFilter("numberList", numbersList);
        advancedPaginator.getSearchModel().addFilter("activateBetween", true);
    }
    
    public void resetFilters(){
        advancedPaginator.resetCache();
    }
}
