/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.model;

import connection.database.DatabaseConnection;
import controller.model.exceptions.IllegalOrphanException;
import controller.model.exceptions.NonexistentEntityException;
import controller.model.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Users;
import model.Choiceset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Exam;

/**
 *
 * @author Dan
 */
public class ExamJpaController implements Serializable {
    public ExamJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exam exam) throws RollbackFailureException, Exception {
        if (exam.getChoicesetList() == null) {
            exam.setChoicesetList(new ArrayList<Choiceset>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Users userid = exam.getUserid();
            if (userid != null) {
                userid = em.getReference(userid.getClass(), userid.getUserid());
                exam.setUserid(userid);
            }
            List<Choiceset> attachedChoicesetList = new ArrayList<Choiceset>();
            for (Choiceset choicesetListChoicesetToAttach : exam.getChoicesetList()) {
                choicesetListChoicesetToAttach = em.getReference(choicesetListChoicesetToAttach.getClass(), choicesetListChoicesetToAttach.getChoicesetid());
                attachedChoicesetList.add(choicesetListChoicesetToAttach);
            }
            exam.setChoicesetList(attachedChoicesetList);
            em.persist(exam);
            if (userid != null) {
                userid.getExamList().add(exam);
                userid = em.merge(userid);
            }
            for (Choiceset choicesetListChoiceset : exam.getChoicesetList()) {
                Exam oldExamidOfChoicesetListChoiceset = choicesetListChoiceset.getExamid();
                choicesetListChoiceset.setExamid(exam);
                choicesetListChoiceset = em.merge(choicesetListChoiceset);
                if (oldExamidOfChoicesetListChoiceset != null) {
                    oldExamidOfChoicesetListChoiceset.getChoicesetList().remove(choicesetListChoiceset);
                    oldExamidOfChoicesetListChoiceset = em.merge(oldExamidOfChoicesetListChoiceset);
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

    public void edit(Exam exam) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Exam persistentExam = em.find(Exam.class, exam.getExamid());
            Users useridOld = persistentExam.getUserid();
            Users useridNew = exam.getUserid();
            List<Choiceset> choicesetListOld = persistentExam.getChoicesetList();
            List<Choiceset> choicesetListNew = exam.getChoicesetList();
            List<String> illegalOrphanMessages = null;
            for (Choiceset choicesetListOldChoiceset : choicesetListOld) {
                if (!choicesetListNew.contains(choicesetListOldChoiceset)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Choiceset " + choicesetListOldChoiceset + " since its examid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (useridNew != null) {
                useridNew = em.getReference(useridNew.getClass(), useridNew.getUserid());
                exam.setUserid(useridNew);
            }
            List<Choiceset> attachedChoicesetListNew = new ArrayList<Choiceset>();
            for (Choiceset choicesetListNewChoicesetToAttach : choicesetListNew) {
                choicesetListNewChoicesetToAttach = em.getReference(choicesetListNewChoicesetToAttach.getClass(), choicesetListNewChoicesetToAttach.getChoicesetid());
                attachedChoicesetListNew.add(choicesetListNewChoicesetToAttach);
            }
            choicesetListNew = attachedChoicesetListNew;
            exam.setChoicesetList(choicesetListNew);
            exam = em.merge(exam);
            if (useridOld != null && !useridOld.equals(useridNew)) {
                useridOld.getExamList().remove(exam);
                useridOld = em.merge(useridOld);
            }
            if (useridNew != null && !useridNew.equals(useridOld)) {
                useridNew.getExamList().add(exam);
                useridNew = em.merge(useridNew);
            }
            for (Choiceset choicesetListNewChoiceset : choicesetListNew) {
                if (!choicesetListOld.contains(choicesetListNewChoiceset)) {
                    Exam oldExamidOfChoicesetListNewChoiceset = choicesetListNewChoiceset.getExamid();
                    choicesetListNewChoiceset.setExamid(exam);
                    choicesetListNewChoiceset = em.merge(choicesetListNewChoiceset);
                    if (oldExamidOfChoicesetListNewChoiceset != null && !oldExamidOfChoicesetListNewChoiceset.equals(exam)) {
                        oldExamidOfChoicesetListNewChoiceset.getChoicesetList().remove(choicesetListNewChoiceset);
                        oldExamidOfChoicesetListNewChoiceset = em.merge(oldExamidOfChoicesetListNewChoiceset);
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
                Integer id = exam.getExamid();
                if (findExam(id) == null) {
                    throw new NonexistentEntityException("The exam with id " + id + " no longer exists.");
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
            Exam exam;
            try {
                exam = em.getReference(Exam.class, id);
                exam.getExamid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exam with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Choiceset> choicesetListOrphanCheck = exam.getChoicesetList();
            for (Choiceset choicesetListOrphanCheckChoiceset : choicesetListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Exam (" + exam + ") cannot be destroyed since the Choiceset " + choicesetListOrphanCheckChoiceset + " in its choicesetList field has a non-nullable examid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userid = exam.getUserid();
            if (userid != null) {
                userid.getExamList().remove(exam);
                userid = em.merge(userid);
            }
            em.remove(exam);
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
    
    public List<Exam> findExamEntities() {
        return findExamEntities(true, -1, -1);
    }

    public List<Exam> findExamEntities(int maxResults, int firstResult) {
        return findExamEntities(false, maxResults, firstResult);
    }

    private List<Exam> findExamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exam.class));
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

    public Exam findExam(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exam.class, id);
        } finally {
            em.close();
        }
    }

    public int getExamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exam> rt = cq.from(Exam.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Exam CastResultSetToExam(ResultSet rs){
        try {
            Exam ex =new Exam(rs.getString("EXAMTITLE"));
            return ex;
        } catch (SQLException e) {
            Logger.getLogger(ExamJpaController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
