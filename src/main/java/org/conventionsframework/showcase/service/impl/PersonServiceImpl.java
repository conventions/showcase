package org.conventionsframework.showcase.service.impl;

import org.conventionsframework.model.WrappedData;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.service.impl.StatefulHibernateService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:35:41 PM
 */
@Stateful
//Turning your service into an EJB will make your methods(the transactionAttribute ones)
// to run within an EJB transaction and auto flush of hibernate session. Note that Conventions will 
//flush hibernate session after Insert,remove or update so your service will still work if its not an EJB
//<code>@see org.conventionsframework.service.impl.BaseServiceImpl</code> to see which methods are Transactional
@Named(value = "personService")
@PersistentClass(Person.class)
public class PersonServiceImpl extends StatefulHibernateService<Person, Long> implements PersonService {

    
    @Override
    public WrappedData<Person> configFindPaginated(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters, Map externalFilter) {

        DetachedCriteria dc = getDao().getDetachedCriteria();
        if (sortField == null || "".equals(sortField)) {
            sortField = "name";
        }
        if (externalFilter != null && !externalFilter.isEmpty()) {
            String name = (String) externalFilter.get("name");
            if (name != null) {
                dc.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
            }
            String lastname = (String) externalFilter.get("lastname");
            if (lastname != null) {
                dc.add(Restrictions.ilike("lastname", lastname, MatchMode.ANYWHERE));
            }
            String age = (String) externalFilter.get("age");
            if (age != null && !StringUtils.isBlank(age)) {
                dc.add(Restrictions.eq("age", new Integer(age)));
            }
            Long ignoreId = (Long) externalFilter.get("ignoreId");
            if (ignoreId != null) {
                dc.add(Restrictions.ne("id", ignoreId));
            }
        }
        /*
         * config prime datatable filter columns
         */
        if (filters != null && !filters.isEmpty()) {
            if (filters.get("name") != null) {
                dc.add(Restrictions.ilike("name", (String) filters.get("name"), MatchMode.ANYWHERE));
            }
            if (filters.get("lastname") != null) {
                dc.add(Restrictions.ilike("lastname", (String) filters.get("lastname"), MatchMode.ANYWHERE));
            }

            if (filters.get("age") != null) {
                dc.add(Restrictions.eq("age", new Integer((String) filters.get("age"))));
            }
        }
        return getDao().findPaginated(first, pageSize, sortField, sortOrder, dc);
    }

    @Override
    public boolean alowDeletePerson(Person entity) {
        if (entity.getAge() > 60) {
            return false;
        }
        return true;
    }
    
     @Override
    public void beforeRemove(Person entity) {
        //override to perform logic before removing an entity
        super.beforeRemove(entity);
    }

    @Override
    public void remove(Person entity) {
        //called by remove button, override to perform logic when removing an entity
        if (this.alowDeletePerson(entity)) {
            super.remove(entity);
        } else {
            throw new BusinessException("Not allowed to remove person above 60 year old.");
        }
    }

    @Override
    public void afterRemove(Person entity) {
        //override to perform logic after removing an entity
        super.afterRemove(entity);
    }

    @Override
    public void beforeStore(Person entity) {
        //override to perform logic before storing an entity
        super.beforeStore(entity);
    }

    @Override
    public void store(Person entity) {
        //called save button click, override to perform logic when storing an entity
        super.store(entity);
    }

    @Override
    public void afterStore(Person entity) {
        //override to perform logic after storing an entity
        super.afterStore(entity);
    }

}