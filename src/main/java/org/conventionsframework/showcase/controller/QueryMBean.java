/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.Phone;
import org.conventionsframework.showcase.util.QueryUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Rafaem M. Pestano - Sep 8, 2012 9:19:57 PM
 */
@ViewAccessScoped
@Named
public class QueryMBean implements Serializable{
    
    @Inject
    private QueryUtils queryUtils;

    private List<Person> query1;
    private List<Phone> query2;
    private List<Person> query3;
    
    @PostConstruct
    public void init(){
        query1 = queryUtils.findPersonByAge();
        query2 = queryUtils.findPhones();
        query3 = queryUtils.findPersonByOne();
    }

    public List<Person> getQuery1() {
        return query1;
    }

    public void setQuery1(List<Person> query1) {
        this.query1 = query1;
    }

    public List<Phone> getQuery2() {
        return query2;
    }

    public void setQuery2(List<Phone> query2) {
        this.query2 = query2;
    }

    public List<Person> getQuery3() {
        return query3;
    }

    public void setQuery3(List<Person> query3) {
        this.query3 = query3;
    }
    
}
