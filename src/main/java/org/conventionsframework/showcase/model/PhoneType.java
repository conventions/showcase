/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.model;


/**
 *
 * @author rmpestano
 */


public enum PhoneType{
    LANDLINE("Landline"),WIRELESS("Wireless"),CELLULAR("Cellular"); 
    
    private final String name;

    public String getName() {
        return name;
    }

    private PhoneType(String name) {
        this.name = name;
    }

    
}
