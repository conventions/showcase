/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.converter;

import org.conventionsframework.converter.AbstractBaseConverter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.myfaces.extensions.cdi.core.api.Advanced;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;

/**
 *
 * @author rmpestano Dec 25, 2011 12:32:50 PM
 */
@Advanced
@FacesConverter(value="personConverter")
public class PersonConverter extends AbstractBaseConverter {
    
    @Inject
    public void setPersonService(@Service(name=Service.STATELESS,entity=Person.class)BaseService personService) {
        super.setBaseService(personService);
    }
	
}
