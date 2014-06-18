/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.converter;

import org.conventionsframework.converter.BaseConverter;
import org.conventionsframework.qualifier.Service;
import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author rmpestano Dec 25, 2011 12:32:50 PM
 */
@FacesConverter(value="personConverter")
public class PersonConverter extends BaseConverter {
    
    @Inject
    public void setPersonService(@Service BaseService<Person> personService) {
        super.setBaseService(personService);
    }
	
}
