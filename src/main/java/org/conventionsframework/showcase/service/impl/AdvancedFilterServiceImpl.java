/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.service.impl;

import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.conventionsframework.dao.impl.StatelessGenericHibernateDao;
import org.conventionsframework.model.WrappedData;
import org.conventionsframework.service.impl.StatelessHibernateService;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.model.PhoneType;
import org.conventionsframework.showcase.service.AdvancedFilterService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.model.SortOrder;



/**
 * 
 * @author Rafael M. Pestano
 * @Date Jun 18, 2012
 * 
 */
@Named("advancedFilterService")
public class AdvancedFilterServiceImpl extends StatelessHibernateService<Person, Long> implements AdvancedFilterService{


    
    /**
     * configure filtering and sort for lazy datatable, this method is called 
     * everytime datatable is updated, you need to override it when you need complex
     * filtering/sort or if you want to change the default filters behavior
     * @see StatelessGenericHibernateDao#addBasicFilterRestrictions(org.hibernate.criterion.DetachedCriteria, java.util.Map) 
     */
    @Override
    public WrappedData<Person> configFindPaginated(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> columnFilters, Map<String, Object> externalFilters) {
        
        DetachedCriteria dc = getDetachedCriteria();
        
        /**
         * keep basic restrictions  as name(iLike),age(eq) and lastName(iLike)
         * on top of the entity being paged(Person), basicFilterRestrictions 
         * is used when you dont override this method
         * @see StatelessGenericHibernateDao#configFindPaginated((int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> columnFilters, Map<String, Object> externalFilters)
         */
        if(columnFilters != null && !columnFilters.isEmpty()){
            getDao().addBasicFilterRestrictions(dc, columnFilters);
        }
        if(externalFilters != null && !externalFilters.isEmpty()){
            getDao().addBasicFilterRestrictions(dc, externalFilters);
        }
        if(externalFilters != null && !externalFilters.isEmpty()){
            String phone = (String) externalFilters.get("phone");
            if(phone != null && !"".endsWith(phone)){
                //create join with Phone table
                dc.createAlias("telephones", "telephones",JoinType.LEFT_OUTER_JOIN);
                dc.add(Restrictions.eq("telephones.number", phone));
            }
            PhoneType type = (PhoneType) externalFilters.get("type");
            if(type != null){
                if(phone == null || phone.trim().equals("")){
                    //if join was not created yet just create it
                    dc.createAlias("telephones", "telephones",JoinType.LEFT_OUTER_JOIN);
                }
                dc.add(Restrictions.eq("telephones.type", type));
            }
            
            Boolean activateBetweenAgesRestriction = (Boolean) externalFilters.get("activateBetween");
            if(activateBetweenAgesRestriction != null && activateBetweenAgesRestriction){
                dc.add(Restrictions.between("age", 1, 10));
            }
            List<String> numberList = (List<String>) externalFilters.get("numberList");
            if(numberList != null){
                if(type == null && (phone == null || phone.trim().equals(""))){
                    //if alias was not created yet
                     dc.createAlias("telephones", "telephones",JoinType.LEFT_OUTER_JOIN);
                     dc.add(Restrictions.in("telephones.number", numberList));
                }
            }
        }
        return super.findPaginated(first, pageSize, sortField, sortOrder, dc);
    }
    
    
}
