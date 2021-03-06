/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Triggers;
import model.Functions;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ove
 */
public class TriggersJpaController implements Serializable {

    public TriggersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public TriggersJpaController() {
        emf = Persistence.createEntityManagerFactory("ConsultasZabbixPU");
    }
    
    public List<Triggers> getTriggersById(long triggerId){
        EntityManager em = getEntityManager();     
        Query q = em.createQuery("SELECT t FROM Triggers t WHERE t.triggerid = :triggerid");
        q.setParameter("triggerid", triggerId);
        return q.getResultList();
    }
}
