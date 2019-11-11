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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Subjects;

/**
 *
 * @author Dan
 */
public class SubjectsJpaController implements Serializable {

    public SubjectsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subjects subjects) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (subjects.getExamList() == null) {
            subjects.setExamList(new ArrayList<Exam>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Exam> attachedExamList = new ArrayList<Exam>();
            for (Exam examListExamToAttach : subjects.getExamList()) {
                examListExamToAttach = em.getReference(examListExamToAttach.getClass(), examListExamToAttach.getExamid());
                attachedExamList.add(examListExamToAttach);
            }
            subjects.setExamList(attachedExamList);
            em.persist(subjects);
            for (Exam examListExam : subjects.getExamList()) {
                Subjects oldSubjectidOfExamListExam = examListExam.getSubjectid();
                examListExam.setSubjectid(subjects);
                examListExam = em.merge(examListExam);
                if (oldSubjectidOfExamListExam != null) {
                    oldSubjectidOfExamListExam.getExamList().remove(examListExam);
                    oldSubjectidOfExamListExam = em.merge(oldSubjectidOfExamListExam);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSubjects(subjects.getSubjectid()) != null) {
                throw new PreexistingEntityException("Subjects " + subjects + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subjects subjects) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subjects persistentSubjects = em.find(Subjects.class, subjects.getSubjectid());
            List<Exam> examListOld = persistentSubjects.getExamList();
            List<Exam> examListNew = subjects.getExamList();
            List<String> illegalOrphanMessages = null;
            for (Exam examListOldExam : examListOld) {
                if (!examListNew.contains(examListOldExam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Exam " + examListOldExam + " since its subjectid field is not nullable.");
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
            subjects.setExamList(examListNew);
            subjects = em.merge(subjects);
            for (Exam examListNewExam : examListNew) {
                if (!examListOld.contains(examListNewExam)) {
                    Subjects oldSubjectidOfExamListNewExam = examListNewExam.getSubjectid();
                    examListNewExam.setSubjectid(subjects);
                    examListNewExam = em.merge(examListNewExam);
                    if (oldSubjectidOfExamListNewExam != null && !oldSubjectidOfExamListNewExam.equals(subjects)) {
                        oldSubjectidOfExamListNewExam.getExamList().remove(examListNewExam);
                        oldSubjectidOfExamListNewExam = em.merge(oldSubjectidOfExamListNewExam);
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
                Integer id = subjects.getSubjectid();
                if (findSubjects(id) == null) {
                    throw new NonexistentEntityException("The subjects with id " + id + " no longer exists.");
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
            Subjects subjects;
            try {
                subjects = em.getReference(Subjects.class, id);
                subjects.getSubjectid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subjects with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Exam> examListOrphanCheck = subjects.getExamList();
            for (Exam examListOrphanCheckExam : examListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subjects (" + subjects + ") cannot be destroyed since the Exam " + examListOrphanCheckExam + " in its examList field has a non-nullable subjectid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(subjects);
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

    public List<Subjects> findSubjectsEntities() {
        return findSubjectsEntities(true, -1, -1);
    }

    public List<Subjects> findSubjectsEntities(int maxResults, int firstResult) {
        return findSubjectsEntities(false, maxResults, firstResult);
    }

    private List<Subjects> findSubjectsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subjects.class));
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

    public Subjects findSubjects(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subjects.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubjectsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subjects> rt = cq.from(Subjects.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
