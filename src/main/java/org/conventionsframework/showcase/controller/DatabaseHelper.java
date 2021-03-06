/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.qualifier.Service;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.Phone;
import org.conventionsframework.showcase.model.PhoneType;
import org.conventionsframework.util.BeanManagerController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael M. Pestano May 13, 2011 10:55:39 PM
 */
@Named(value="databaseHelper")
@SessionScoped
public class DatabaseHelper implements Serializable{
    
    @Inject
    @Service
    private BaseService<Person> baseService;
    
    private boolean aplicattionInitialized;
    
    public void initDatabase(){
         /* populate database */
        try{
            
        if (baseService.crud().countAll() == 0) {
            for (int i = 0; i < 1000; i++) {
                Person p = new Person("Person " + i, "Lastname " + i, i % 100);
                p.setTelephones(generateTelephones(i));
                baseService.store(p);
            }
        }
        AccessCountMBean countBean =  BeanManagerController.getBeanByType(AccessCountMBean.class);
        countBean.increment();
        }finally{
             aplicattionInitialized = true;
        }
    }

    
    
    public boolean isAplicattionInitialized() {
        return aplicattionInitialized;
    }

    public void setAplicattionInitialized(boolean aplicattionInitialized) {
        this.aplicattionInitialized = aplicattionInitialized;
    }

    private List<Phone> generateTelephones(int i) {
        List<Phone> phoneList = new ArrayList<Phone>();
        if(i%2 == 0){
            phoneList.add(createPhone(i%10));
            phoneList.add(createPhone((i+1)%10));
        }
        else{
             phoneList.add(createPhone(i%10));
        }
        return phoneList;
    }

    private Phone createPhone(int i) {
          Phone phone = new Phone();
          phone.setNumber(i+""+i+""+i+""+i+""+i+""+i+""+i+""+i+"");
           if(i%7 == 0){
                phone.setType(PhoneType.WIRELESS);
            }
            else if(i%5 == 0){
                phone.setType(PhoneType.LANDLINE);
            }
            else{
                phone.setType(PhoneType.CELLULAR);
            }
          return  phone;
    }
    
    
}
