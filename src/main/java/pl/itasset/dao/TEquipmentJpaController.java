package pl.itasset.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import pl.itasset.dao.exceptions.NonexistentEntityException;
import pl.itasset.dao.exceptions.PreexistingEntityException;
import pl.itasset.dao.exceptions.RollbackFailureException;
import pl.itasset.entity.TEquipment;

public class TEquipmentJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UserTransaction utx;
    private final EntityManagerFactory emf;

    public TEquipmentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TEquipment tEquipment) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(tEquipment);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTEquipment(tEquipment.getId()) != null) {
                throw new PreexistingEntityException("TEquipment " + tEquipment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TEquipment tEquipment) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            tEquipment = em.merge(tEquipment);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.isEmpty()) {
                Long id = tEquipment.getId();
                if (findTEquipment(id) == null) {
                    throw new NonexistentEntityException("The tEquipment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TEquipment tEquipment;
            try {
                tEquipment = em.getReference(TEquipment.class, id);
                tEquipment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tEquipment with id " + id + " no longer exists.", enfe);
            }
            em.remove(tEquipment);
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

    public List<TEquipment> findTEquipmentEntities() {
        return findTEquipmentEntities(true, -1, -1);
    }

    public List<TEquipment> findTEquipmentEntities(int maxResults, int firstResult) {
        return findTEquipmentEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<TEquipment> findTEquipmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TEquipment.class));
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

    public TEquipment findTEquipment(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TEquipment.class, id);
        } finally {
            em.close();
        }
    }

    public int getTEquipmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<TEquipment> rt = cq.from(TEquipment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
