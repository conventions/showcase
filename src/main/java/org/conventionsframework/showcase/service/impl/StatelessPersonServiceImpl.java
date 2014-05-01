package org.conventionsframework.showcase.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.model.SearchModel;
import org.conventionsframework.service.impl.BaseServiceImpl;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.StatelessPersonService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:35:41 PM
 */
@Stateless
public class StatelessPersonServiceImpl extends BaseServiceImpl<Person> implements
        StatelessPersonService {

    

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public DetachedCriteria configPagination(SearchModel<Person> searchModel) {
        DetachedCriteria dc = getDao().getDetachedCriteria();

        Map<String,Object> filter = searchModel.getFilter();
        if (filter != null && !filter.isEmpty()) {
            String name = (String) filter.get("name");
            if (name != null) {
                dc.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
            }
            String lastname = (String) filter.get("lastname");
            if (lastname != null) {
                dc.add(Restrictions.ilike("lastname", lastname, MatchMode.ANYWHERE));
            }
            String age = (String) filter.get("age");
            if(age != null && !StringUtils.isBlank(age)){
                dc.add(Restrictions.eq("age", new Integer(age)));
            }
            Long ignoreId = (Long) filter.get("ignoreId");
            if(ignoreId != null){
                dc.add(Restrictions.ne("id", ignoreId));
            }
        }

        //NOTE all the restrictions above are unnecessary cause Conventions can infer restrictions via reflection
        //for basic fields like above(not relationships) and will do a ilike for String fields and eq for long,integer/date fields
        // if you want to use this behavior just return super.configFindPaginated(columnFilters, externalFilter, dc);
        return super.configPagination(searchModel,dc);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
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
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
