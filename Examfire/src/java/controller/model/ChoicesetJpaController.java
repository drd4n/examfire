/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.model;

import controller.model.exceptions.IllegalOrphanException;
import controller.model.exceptions.NonexistentEntityException;
import controller.model.exceptions.PreexistingEntityException;
import controller.model.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Exam;
import model.Choice;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Choiceset;

/**
 *
 * @author Dan
 */
public class ChoicesetJpaController implements Serializable {

    public ChoicesetJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Choiceset choiceset) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (choiceset.getChoiceList() == null) {
            choiceset.setChoiceList(new ArrayList<Choice>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Exam examid = choiceset.getExamid();
            if (examid != null) {
                examid = em.getReference(examid.getClass(), examid.getExamid());
                choiceset.setExamid(examid);
            }
            List<Choice> attachedChoiceList = new ArrayList<Choice>();
            for (Choice choiceListChoiceToAttach : choiceset.getChoiceList()) {
                choiceListChoiceToAttach = em.getReference(choiceListChoiceToAttach.getClass(), choiceListChoiceToAttach.getChoiceid());
                attachedChoiceList.add(choiceListChoiceToAttach);
            }
            choiceset.setChoiceList(attachedChoiceList);
            em.persist(choiceset);
            if (examid != null) {
                examid.getChoicesetList().add(choiceset);
                examid = em.merge(examid);
            }
            for (Choice choiceListChoice : choiceset.getChoiceList()) {
                Choiceset oldChoicesetidOfChoiceListChoice = choiceListChoice.getChoicesetid();
                choiceListChoice.setChoicesetid(choiceset);
                choiceListChoice = em.merge(choiceListChoice);
                if (oldChoicesetidOfChoiceListChoice != null) {
                    oldChoicesetidOfChoiceListChoice.getChoiceList().remove(choiceListChoice);
                    oldChoicesetidOfChoiceListChoice = em.merge(oldChoicesetidOfChoiceListChoice);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findChoiceset(choiceset.getChoicesetid()) != null) {
                throw new PreexistingEntityException("Choiceset " + choiceset + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Choiceset choiceset) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choiceset persistentChoiceset = em.find(Choiceset.class, choiceset.getChoicesetid());
            Exam examidOld = persistentChoiceset.getExamid();
            Exam examidNew = choiceset.getExamid();
            List<Choice> choiceListOld = persistentChoiceset.getChoiceList();
            List<Choice> choiceListNew = choiceset.getChoiceList();
            List<String> illegalOrphanMessages = null;
            for (Choice choiceListOldChoice : choiceListOld) {
                if (!choiceListNew.contains(choiceListOldChoice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Choice " + choiceListOldChoice + " since its choicesetid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (examidNew != null) {
                examidNew = em.getReference(examidNew.getClass(), examidNew.getExamid());
                choiceset.setExamid(examidNew);
            }
            List<Choice> attachedChoiceListNew = new ArrayList<Choice>();
            for (Choice choiceListNewChoiceToAttach : choiceListNew) {
                choiceListNewChoiceToAttach = em.getReference(choiceListNewChoiceToAttach.getClass(), choiceListNewChoiceToAttach.getChoiceid());
                attachedChoiceListNew.add(choiceListNewChoiceToAttach);
            }
            choiceListNew = attachedChoiceListNew;
            choiceset.setChoiceList(choiceListNew);
            choiceset = em.merge(choiceset);
            if (examidOld != null && !examidOld.equals(examidNew)) {
                examidOld.getChoicesetList().remove(choiceset);
                examidOld = em.merge(examidOld);
            }
            if (examidNew != null && !examidNew.equals(examidOld)) {
                examidNew.getChoicesetList().add(choiceset);
                examidNew = em.merge(examidNew);
            }
            for (Choice choiceListNewChoice : choiceListNew) {
                if (!choiceListOld.contains(choiceListNewChoice)) {
                    Choiceset oldChoicesetidOfChoiceListNewChoice = choiceListNewChoice.getChoicesetid();
                    choiceListNewChoice.setChoicesetid(choiceset);
                    choiceListNewChoice = em.merge(choiceListNewChoice);
                    if (oldChoicesetidOfChoiceListNewChoice != null && !oldChoicesetidOfChoiceListNewChoice.equals(choiceset)) {
                        oldChoicesetidOfChoiceListNewChoice.getChoiceList().remove(choiceListNewChoice);
                        oldChoicesetidOfChoiceListNewChoice = em.merge(oldChoicesetidOfChoiceListNewChoice);
                    }
                }
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
                Integer id = choiceset.getChoicesetid();
                if (findChoiceset(id) == null) {
                    throw new NonexistentEntityException("The choiceset with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Choiceset choiceset;
            try {
                choiceset = em.getReference(Choiceset.class, id);
                choiceset.getChoicesetid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The choiceset with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Choice> choiceListOrphanCheck = choiceset.getChoiceList();
            for (Choice choiceListOrphanCheckChoice : choiceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Choiceset (" + choiceset + ") cannot be destroyed since the Choice " + choiceListOrphanCheckChoice + " in its choiceList field has a non-nullable choicesetid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Exam examid = choiceset.getExamid();
            if (examid != null) {
                examid.getChoicesetList().remove(choiceset);
                examid = em.merge(examid);
            }
            em.remove(choiceset);
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

    public List<Choiceset> findChoicesetEntities() {
        return findChoicesetEntities(true, -1, -1);
    }

    public List<Choiceset> findChoicesetEntities(int maxResults, int firstResult) {
        return findChoicesetEntities(false, maxResults, firstResult);
    }

    private List<Choiceset> findChoicesetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Choiceset.class));
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

    public Choiceset findChoiceset(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Choiceset.class, id);
        } finally {
            em.close();
        }
    }

    public int getChoicesetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Choiceset> rt = cq.from(Choiceset.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
