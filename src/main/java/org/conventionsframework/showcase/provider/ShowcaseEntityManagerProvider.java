/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.conventionsframework.showcase.provider;

import org.conventionsframework.showcase.controller.ComboMBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.conventionsframework.entitymanager.CustomEntityManagerProvider;
import org.conventionsframework.entitymanager.EntityManagerProvider;
import org.conventionsframework.qualifier.ConventionsEntityManager;
import org.conventionsframework.qualifier.Type;

/**
 * entityManager provider for CustomHibernateService
 * @see ComboMBean#initList(org.conventionsframework.service.BaseService) 
 * @author rmpestano
 */
@Specializes
@RequestScoped
@ConventionsEntityManager(type=Type.CUSTOM)
public class ShowcaseEntityManagerProvider extends CustomEntityManagerProvider implements EntityManagerProvider {

    @PersistenceContext(unitName="anotherUnit")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

     
}
