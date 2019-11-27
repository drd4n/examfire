/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.model;

import controller.model.exceptions.NonexistentEntityException;
import controller.model.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Choice;
import model.Choiceset;

/**
 *
 * @author Dan
 */
public class ChoiceJpaController implements Serializable {

    public ChoiceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Choice choice) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choiceset choicesetid = choice.getChoicesetid();
            if (choicesetid != null) {
                choicesetid = em.getReference(choicesetid.getClass(), choicesetid.getChoicesetid());
                choice.setChoicesetid(choicesetid);
            }
            em.persist(choice);
            if (choicesetid != null) {
                choicesetid.getChoiceList().add(choice);
                choicesetid = em.merge(choicesetid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Choice choice) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choice persistentChoice = em.find(Choice.class, choice.getChoiceid());
            Choiceset choicesetidOld = persistentChoice.getChoicesetid();
            Choiceset choicesetidNew = choice.getChoicesetid();
            if (choicesetidNew != null) {
                choicesetidNew = em.getReference(choicesetidNew.getClass(), choicesetidNew.getChoicesetid());
                choice.setChoicesetid(choicesetidNew);
            }
            choice = em.merge(choice);
            if (choicesetidOld != null && !choicesetidOld.equals(choicesetidNew)) {
                choicesetidOld.getChoiceList().remove(choice);
                choicesetidOld = em.merge(choicesetidOld);
            }
            if (choicesetidNew != null && !choicesetidNew.equals(choicesetidOld)) {
                choicesetidNew.getChoiceList().add(choice);
                choicesetidNew = em.merge(choicesetidNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = choice.getChoiceid();
                if (findChoice(id) == null) {
                    throw new NonexistentEntityException("The choice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choice choice;
            try {
                choice = em.getReference(Choice.class, id);
                choice.getChoiceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The choice with id " + id + " no longer exists.", enfe);
            }
            Choiceset choicesetid = choice.getChoicesetid();
            if (choicesetid != null) {
                choicesetid.getChoiceList().remove(choice);
                choicesetid = em.merge(choicesetid);
            }
            em.remove(choice);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Choice> findChoiceEntities() {
        return findChoiceEntities(true, -1, -1);
    }

    public List<Choice> findChoiceEntities(int maxResults, int firstResult) {
        return findChoiceEntities(false, maxResults, firstResult);
    }

    private List<Choice> findChoiceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Choice.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Choice findChoice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Choice.class, id);
        } finally {
            em.close();
        }
    }

    public int getChoiceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Choice> rt = cq.from(Choice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
