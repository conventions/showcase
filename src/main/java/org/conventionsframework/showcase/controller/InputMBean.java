/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.hibernate.validator.constraints.NotEmpty;

import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;
 
/**
 *
 * @author Rafael M. Pestano Apr 4, 2011 7:58:11 PM
 */
@Named("inputMBean")
@ViewAccessScoped
public class InputMBean implements Serializable {
   private String requiredInput;
   private String password;
   private Integer number;

    @NotEmpty(message="This field is required")
    public String getRequiredInput() {

        return requiredInput;
    }

    public void setRequiredInput(String requiredInput) {
        this.requiredInput = requiredInput;
    }


    @Size(min=6,max=100,message="Minimum size 6 and maximum size is 100")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
 
    
    
    
}
