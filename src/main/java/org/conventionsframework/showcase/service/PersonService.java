package org.conventionsframework.showcase.service;

import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:33:35 PM
 */
public interface PersonService extends BaseService<Person, Long>{

     boolean alowDeletePerson(Person entity);
     void setRollbackTest(boolean rollbackTest);
}
