/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;
import org.conventionsframework.model.BaseEntityLong;

/**
 *
 * @author VAIO
 */
@Entity
@NamedQuery(name="Phone.findByNumber", query="Select p FROM Phone p where p.number = :number")
public class Phone extends BaseEntityLong{
    
    private String number;
    @Enumerated(EnumType.STRING)
    private PhoneType type; 

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
    
    
}
