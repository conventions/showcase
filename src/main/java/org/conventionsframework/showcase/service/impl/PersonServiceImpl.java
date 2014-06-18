package org.conventionsframework.showcase.service.impl;

import org.conventionsframework.crud.CriteriaBuilder;
import org.conventionsframework.exception.BusinessException;
import org.conventionsframework.model.SearchModel;
import org.conventionsframework.qualifier.Log;
import org.conventionsframework.service.impl.BaseServiceImpl;
import org.conventionsframework.showcase.model.Person;
import org.conventionsframework.showcase.service.PersonService;
import org.conventionsframework.util.Assert;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Rafael M. Pestano Mar 21, 2011 4:35:41 PM
 */
//Turning your service into an EJB will make your methods(the transactionAttribute ones)
// to run within an EJB transaction and auto flush of hibernate session. Note that Conventions will 
//flush hibernate session after Insert,remove or update so your service will still work if its not an EJB
//<code>@see org.conventionsframework.service.impl.BaseServiceImpl</code> to see which methods are Transactional
@Stateful
@Named(value = "personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {
    
    private boolean rollbackTest;

    @Inject @Log
    private transient Logger log;

    @Inject
    CriteriaBuilder<Person> builder;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;



    //override default entityManager which is type = TRANSACTION
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EntityManager getEntityManager() {
        return em;
    }



  /*  example of how to access each page returned by pagination
    @Override
    public PaginationResult&lt;Person&gt; executePagination(SearchModel&lt;Person&gt; searchModel, Criteria criteria) {
        if(searchModel.getSortField() == null){
            //table comes pre sorted so if no sort is applied sort by name
            searchModel.setSortField("name");
        }
        PaginationResult&lt;Person&gt; paginationResult = super.executePagination(searchModel,criteria);
        List&lt;Person&gt; modifiedPage = paginationResult.getPage();
        //you can modify the page if want, eg: set a transient field in your entity depending on some business logic
        modifiedPage.remove(0);
        paginationResult.setPage(modifiedPage);
        //note that if you remove/add a record from/to the page here make sure you subtract/add rowCount
        int modifiedRowCount = paginationResult.getRowCount() -1;
        paginationResult.setRowCount(modifiedRowCount);//you can also access rowCount
        return paginationResult;//feed lazy primrfaces datatable with modified page
    }
    */


    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Criteria configPagination(SearchModel<Person> searchModel) {
        Map<String,Object> filter = searchModel.getDatatableFilter();
        if (filter != null && !filter.isEmpty()) {
            String name = (String) filter.get("name");
            builder.ilike("name", name, MatchMode.ANYWHERE);
            String lastname = (String) filter.get("lastname");
            builder.ilike("lastname", lastname, MatchMode.ANYWHERE);
            String age = (String) filter.get("age");
            if(Assert.hasText(age)){
                builder.eq("age", new Integer(age));
            }
            Long ignoreId = (Long) filter.get("ignoreId");
            builder.ne("id", ignoreId);
        }
        //NOTE all the restrictions above are unnecessary cause Conventions can infer restrictions via reflection
        //for basic fields like above(not relationships) and will do a ilike for String fields and eq for long,integer/date fields
        // if you want to use this behavior just return super.configPagination(searchModel,criteria);
        return configPagination(searchModel,builder.buildCriteria(crud.getSession()));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean alowDeletePerson(Person entity) {
        if (entity.getAge() > 60) {
            return false;
        }
        return true;
    }
    
     /**
      * IF your Service is an EJB the methods
      * after/before store/remove will run within an EJB transaction
      * so throwing an runtime exception will make the transaction 
      * to be rolled back. Also note that BusinessExcetions are 
      * ApplictionException(rollback=true) so rollback will also be performed when they are thrown
      */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void beforeStore(Person entity) {
        //override to perform logic before storing an entity
        super.beforeStore(entity);
    }
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void afterStore(Person entity) {
          //override to perform logic after storing an entity
          if(rollbackTest){
            // runtime will make the transaction rollback and user will be redirected to error page 
            throw new RuntimeException("Rolling back the transaction, all changes will be lost and your EJB instance will be lost.");
        }
       
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void beforeRemove(Person entity) {
          //override to perform logic before removing an entity

        if(!this.alowDeletePerson(entity)) {
              //BusinessException is ApplicationException so rollback will be performed
              //and FacesMessage error will be queued
            throw new BusinessException("Not allowed to remove person above 60 year old.");
        }
        /* same as if above:
        Assert.isTrue(this.alowDeletePerson(entity),"Not allowed to remove person above 60 year old.");
        */
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void afterRemove(Person entity) {
        //override to perform logic after removing an entity
        super.afterRemove(entity);
    }


    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void setRollbackTest(boolean rollbackTest) {
        this.rollbackTest = rollbackTest;
    }

}