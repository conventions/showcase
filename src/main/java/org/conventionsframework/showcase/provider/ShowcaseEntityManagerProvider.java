/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.provider;

import org.conventionsframework.showcase.controller.ComboMBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.conventionsframework.qualifier.ConventionsEntityManager;

/**
 * entityManager provider for CustomHibernateService
 * @see ComboMBean#initList(org.conventionsframework.service.BaseService) 
 * @author rmpestano
 */
@RequestScoped
public class ShowcaseEntityManagerProvider {

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    @RequestScoped
    @ConventionsEntityManager
    public EntityManager getEntityManager() {
        return entityManager;
    }

     
}
