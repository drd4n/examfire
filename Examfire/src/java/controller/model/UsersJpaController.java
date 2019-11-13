/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.model;

import controller.model.exceptions.IllegalOrphanException;
import controller.model.exceptions.NonexistentEntityException;
import controller.model.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Exam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Users;

/**
 *
 * @author Dan
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws RollbackFailureException, Exception {
        if (users.getExamList() == null) {
            users.setExamList(new ArrayList<Exam>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Exam> attachedExamList = new ArrayList<Exam>();
            for (Exam examListExamToAttach : users.getExamList()) {
                examListExamToAttach = em.getReference(examListExamToAttach.getClass(), examListExamToAttach.getExamid());
                attachedExamList.add(examListExamToAttach);
            }
            users.setExamList(attachedExamList);
            em.persist(users);
            for (Exam examListExam : users.getExamList()) {
                Users oldUesridOfExamListExam = examListExam.getUesrid();
                examListExam.setUesrid(users);
                examListExam = em.merge(examListExam);
                if (oldUesridOfExamListExam != null) {
                    oldUesridOfExamListExam.getExamList().remove(examListExam);
                    oldUesridOfExamListExam = em.merge(oldUesridOfExamListExam);
                }
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

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Users persistentUsers = em.find(Users.class, users.getUserid());
            List<Exam> examListOld = persistentUsers.getExamList();
            List<Exam> examListNew = users.getExamList();
            List<String> illegalOrphanMessages = null;
            for (Exam examListOldExam : examListOld) {
                if (!examListNew.contains(examListOldExam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Exam " + examListOldExam + " since its uesrid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Exam> attachedExamListNew = new ArrayList<Exam>();
            for (Exam examListNewExamToAttach : examListNew) {
                examListNewExamToAttach = em.getReference(examListNewExamToAttach.getClass(), examListNewExamToAttach.getExamid());
                attachedExamListNew.add(examListNewExamToAttach);
            }
            examListNew = attachedExamListNew;
            users.setExamList(examListNew);
            users = em.merge(users);
            for (Exam examListNewExam : examListNew) {
                if (!examListOld.contains(examListNewExam)) {
                    Users oldUesridOfExamListNewExam = examListNewExam.getUesrid();
                    examListNewExam.setUesrid(users);
                    examListNewExam = em.merge(examListNewExam);
                    if (oldUesridOfExamListNewExam != null && !oldUesridOfExamListNewExam.equals(users)) {
                        oldUesridOfExamListNewExam.getExamList().remove(examListNewExam);
                        oldUesridOfExamListNewExam = em.merge(oldUesridOfExamListNewExam);
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
                Integer id = users.getUserid();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUserid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Exam> examListOrphanCheck = users.getExamList();
            for (Exam examListOrphanCheckExam : examListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Exam " + examListOrphanCheckExam + " in its examList field has a non-nullable uesrid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
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

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
