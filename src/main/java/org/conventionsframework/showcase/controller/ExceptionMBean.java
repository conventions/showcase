/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.controller;

import org.conventionsframework.exception.BusinessException;
import java.io.Serializable;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Rafael M. Pestano Apr 4, 2011 7:58:11 PM
 */
@Named("exceptionMBean")
@ViewAccessScoped
public class ExceptionMBean implements Serializable{
    
   
   public void doBusinessLogicException(){
       if(1 < 2 && (1+1 == 2)){
           throw new BusinessException("Business Logic exception...");
       }
   }
   
   public void doUncheckedException(){
       System.out.println(1/0);
   }
   
   public void focusOnError(){
       throw new BusinessException("focus on error is useful to show user where error is", "exceptionForm:idToFocus");
   }
    
}
