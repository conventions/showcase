/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.model;

import org.conventionsframework.model.SelectItemAware;

/**
 *
 * @author rmpestano
 */


public enum PhoneType implements SelectItemAware{
    LANDLINE("Landline"),WIRELESS("Wireless"),CELLULAR("Cellular"); 
    
    private final String name;

    public String getName() {
        return name;
    }

    private PhoneType(String name) {
        this.name = name;
    }

    @Override
    public String getLabel() {
        return name;//selectItem label for conventions:combo component
    }
    
}
