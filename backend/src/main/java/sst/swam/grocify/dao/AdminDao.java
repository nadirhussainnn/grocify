package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sst.swam.grocify.model.Admin;

@ApplicationScoped
public class AdminDao {

    @PersistenceContext
    EntityManager em;

    public Admin findById(Long id) {
        return em.find(Admin.class, id);
    }

    public Admin findByEmail(String email) {
        return em.createQuery("SELECT a FROM Admin a WHERE a.email = :email", Admin.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    @Transactional
    public void save(Admin admin) {
        em.persist(admin);
    }

    @Transactional
    public Admin update(Admin admin) {
        return em.merge(admin);
    }

    @Transactional
    public void delete(Long id) {
        Admin admin = findById(id);
        if (admin != null) {
            em.remove(admin);
        }
    }
}
