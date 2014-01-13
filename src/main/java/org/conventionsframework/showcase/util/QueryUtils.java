/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conventionsframework.showcase.util;

import org.conventionsframework.qualifier.Query;
import org.conventionsframework.qualifier.QueryParam;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.Phone;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.showcase.service.StatelessPersonService;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Rafaem M. Pestano - Sep 8, 2012 6:46:58 PM
 * 
 * Example of QueryUtils using Query interceptor to execute simple annotatation based queries
 */
public class QueryUtils implements Serializable{

    
    @Query(service= StatelessPersonService.class,sql="select p from Person p")
    public List<Person> findAllPerson(){return null;}//the return is made by the framework query interceptor
    
    @Query(service= StatelessPersonService.class,namedQuery="Phone.findByNumber")
    @QueryParam(name="number",value="11111111")
    public List<Phone> findPhones(){return null;}
    
    @Query(namedQuery="Person.One",service= PersonService.class)
    public List<Person> findPersonByOne(){return null;}
    
    @Query(namedQuery="Person.findByAge",service = BaseService.class)//uses default service which is statelessHibernateService
    @QueryParam(name="age",intValue=10)
    public List<Person> findPersonByAge(){return null;}
   
}
