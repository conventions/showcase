/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.service.impl;

import org.conventionsframework.model.SearchModel;
import org.conventionsframework.service.impl.BaseServiceImpl;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.PhoneType;
import org.conventionsframework.showcase.service.AdvancedFilterService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import javax.ejb.Stateful;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael M. Pestano
 * @Date Jun 18, 2012
 *
 */
@Named("advancedFilterService")
@Stateful
public class AdvancedFilterServiceImpl extends BaseServiceImpl<Person> implements AdvancedFilterService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }


    /**
     * configure filtering and sort for lazy datatable, this method is called
     * everytime datatable is updated, you need to override it when you need
     * complex filtering/sort or if you want to change the default filters
     * behavior
     *
     *
     */
    @Override
    public DetachedCriteria configPagination(SearchModel<Person> searchModel) {
        Map<String,Object> filter = searchModel.getFilter();
        DetachedCriteria dc = getDetachedCriteria();
        Person searchEntity = searchModel.getEntity();
        boolean alreadyJoinedPhone = false;
        if (filter != null && !filter.isEmpty()) {
            Boolean activateBetweenAgesRestriction = (Boolean) filter.get("activateBetween");
            if (activateBetweenAgesRestriction != null && activateBetweenAgesRestriction) {
                dc.add(Restrictions.between("age", 1, 10));
            }
            List<String> numberList = (List<String>) filter.get("numberList");
            if (numberList != null) {
                if (!alreadyJoinedPhone) {
                    alreadyJoinedPhone = true;
                    //if alias was not created yet
                    dc.createAlias("telephones", "telephones", JoinType.LEFT_OUTER_JOIN);
                    dc.add(Restrictions.in("telephones.number", numberList));
                }
            }
        }
        String phone = searchEntity.getPhoneNumber();
        if (phone != null && !"".endsWith(phone)) {
            //create join with Phone table
            if (!alreadyJoinedPhone) {
                dc.createAlias("telephones", "telephones", JoinType.LEFT_OUTER_JOIN);
                alreadyJoinedPhone = true;
            }
            dc.add(Restrictions.eq("telephones.number", phone));
        }

        PhoneType type = searchEntity.getPhoneType();
        if (type != null) {
            if (!alreadyJoinedPhone) {
                //if join was not created yet just create it
                dc.createAlias("telephones", "telephones", JoinType.LEFT_OUTER_JOIN);
                alreadyJoinedPhone = true;
            }
            dc.add(Restrictions.eq("telephones.type", type));
        }

        if (!alreadyJoinedPhone) {
            //create join to sort by phone number
            dc.createAlias("telephones", "telephones");
        }
         //NOTE Conventions will infer restrictions via reflection for basic fields like above(not relationships)
         //and will do a ilike for String fields and eq for long,integer/date fields
         // if you want to use this behavior just return super.configFindPaginated(columnFilters, externalFilter, dc);
        //otherwise just return your criteria return dc;
        return super.configPagination(searchModel, dc);
    }
}
