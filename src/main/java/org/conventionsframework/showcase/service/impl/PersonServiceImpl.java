package org.conventionsframework.showcase.service.impl;

import org.conventionsframework.model.WrappedData;
import org.conventionsframework.qualifier.PersistentClass;
import org.conventionsframework.service.impl.StatefulHibernateService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:35:41 PM
 */
@Named(value = "personService")
@PersistentClass(Person.class)
@Stateful
public class PersonServiceImpl extends StatefulHibernateService<Person, Long> implements PersonService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @PostConstruct
    public void test(){
        getDao().setEntityManager(entityManager);
    }
    
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

}