package org.conventionsframework.showcase.service.impl;

import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.model.WrappedData;
import org.conventionsframework.service.impl.StatelessHibernateService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.StatelessPersonService;
import java.lang.Long;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.custom.ScalarReturn;
import org.hibernate.type.LongType;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:35:41 PM
 */
@Named(value="hibernateStatelessPersonService")
public class StatelessPersonServiceImpl extends StatelessHibernateService<Person, Long> implements
        StatelessPersonService {

    

    @Override
    public WrappedData<Person> configFindPaginated(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters, Map externalFilter) {
         DetachedCriteria dc = getDao().getDetachedCriteria();
        if(sortField == null || "".equals(sortField)){
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
            if(age != null && !StringUtils.isBlank(age)){
                dc.add(Restrictions.eq("age", new Integer(age)));
            }
            Long ignoreId = (Long) externalFilter.get("ignoreId");
            if(ignoreId != null){
                dc.add(Restrictions.ne("id", ignoreId));
            }
        }
        /* config prime datatable filter columns */
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
    public boolean alowDeletePerson(Person p) {
        if (p.getAge() > 60) {
            return false;
        }
        return true;
    }
    
    @Override
    public void remove(Person entity) {
        if(this.alowDeletePerson(entity)){
            super.remove(entity);
        }
        else{
            throw new BusinessException("Not allowed to remove person above 60 year old.");
        }
    }
    

    @Override
    public List<Person> findFriends(final Long personID) {
       String query = "select p.friends_id from person_person p where p.person_id = :id";
       Query q = getEntityManager().createNativeQuery(query);
       q.setParameter("id", personID);
       List friendsId = q.getResultList(); 
       if(friendsId != null && !friendsId.isEmpty()){
           List<Long> ids = new ArrayList<Long>();
           for (Object id : friendsId) {
              //dependending on the database the native query returns a list of Long or BigInteger
              //if we had an intermediate table like PersonFriend there was no need for the navive
              ids.add(new Long(id.toString()));
           }
           DetachedCriteria dc = getDetachedCriteria();
           dc.add(Restrictions.in("id", ids));
           return getDao().findByCriteria(dc);
       }

       return null;
    }
     
}
