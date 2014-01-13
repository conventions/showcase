package org.conventionsframework.showcase.security;

import org.conventionsframework.qualifier.Config;
import org.conventionsframework.security.DefaultSecurityContext;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Specializes;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by rmpestano on 1/12/14.
 */
@SessionScoped
@Specializes
public class ShowcaseSecurityContext extends DefaultSecurityContext {

    @Inject
    @Config
    transient Instance<ExternalContext> externalContext;

    @Override
    public Boolean hasRole(String role) {
        List<String> userRoles = getUserRoles();
        return userRoles != null && userRoles.contains(role);
    }

    @Override
    public Boolean hasAnyRole(String[] role) {
        for (String s : role) {
            if(hasRole(s)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public List<String> getUserRoles(){
        //user role(s) should be extracted from current logged user
        //we just put the role in the session for testing purposes @see SecurityMBean#changeProfile()
        return (List<String>) externalContext.get().getSessionMap().get("userRoles");
    }
}
