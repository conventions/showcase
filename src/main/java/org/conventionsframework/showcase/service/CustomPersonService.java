package org.conventionsframework.showcase.service;

import org.conventionsframework.service.BaseService;
import org.conventionsframework.showcase.model.Person;
import java.util.List;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:33:35 PM
 */
public interface CustomPersonService extends BaseService<Person, Long>{

    boolean alowDeletePerson(Person entity);

    public List<Person> findFriends(Long id);
}
