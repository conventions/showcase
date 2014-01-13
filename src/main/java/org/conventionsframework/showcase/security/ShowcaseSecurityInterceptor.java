/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.security;

import org.conventionsframework.qualifier.SecurityMethod;
import org.conventionsframework.security.BaseSecurityInterceptor;
import org.conventionsframework.security.SecurityContext;

import javax.inject.Inject;
import javax.interceptor.Interceptor;

/**
 *
 * @author rmpestano Nov 20, 2011 9:46:59 PM
 */

@SecurityMethod
@Interceptor
public class ShowcaseSecurityInterceptor extends BaseSecurityInterceptor{

    @Inject
    SecurityContext securityContext;

    /**
     * this method is responsible for deciding if current user has permission 
     * to execute a method
     * 
     * @param rolesAllowed roles passed in the method
     * @return true if user has permission, false otherwise
     */
    @Override
    public boolean checkUserPermissions(String[] rolesAllowed) {
        return securityContext.hasAnyRole(rolesAllowed);
    }
    
}
