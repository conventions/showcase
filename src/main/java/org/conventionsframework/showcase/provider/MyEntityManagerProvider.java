/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.conventionsframework.showcase.provider;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.conventionsframework.entitymanager.EntityManagerProvider;

/**
 *
 * @author Rafaem M. Pestano - Sep 12, 2012 1:05:46 PM
 */
@Stateless
@Dependent
@Named("myProvider")
public class MyEntityManagerProvider implements EntityManagerProvider{

    @PersistenceContext
    private EntityManager entityManager;
    
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
