/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conventionsframework.showcase.util;

import java.io.Serializable;
import java.util.List;
import org.conventionsframework.qualifier.ConventionsEntityManager;
import org.conventionsframework.qualifier.Query;
import org.conventionsframework.qualifier.QueryParam;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.Phone;

/**
 *
 * @author Rafaem M. Pestano - Sep 8, 2012 6:46:58 PM
 * 
 * Example of QueryUtils using Query interceptor to execute simple annotatation based queries
 */
public class QueryUtils implements Serializable{

    
    @Query(entityManagerPrivider=ConventionsEntityManager.STATELESS_ENTITY_MANAGER,sql="select p from Person p")
    public List<Person> findAllPerson(){return null;}//the return is made by the framework query interceptor
    
    @Query(entityManagerPrivider=ConventionsEntityManager.STATELESS_ENTITY_MANAGER,namedQuery="Phone.findByNumber")
    @QueryParam(name="number",value="11111111")
    public List<Phone> findPhones(){return null;}
    
    @Query(namedQuery="Person.One")
    public List<Person> findPersonByOne(){return null;}
    
    @Query(namedQuery="Person.findByAge")
    @QueryParam(name="age",intValue=10)
    public List<Person> findPersonByAge(){return null;}
   
}
