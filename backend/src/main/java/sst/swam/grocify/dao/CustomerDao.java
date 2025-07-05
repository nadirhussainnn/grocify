
// DAO: CustomerDao.java
package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sst.swam.grocify.model.Customer;

import java.util.List;

@ApplicationScoped
public class CustomerDao {

    @PersistenceContext
    EntityManager em;

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public Customer findByEmail(String email) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Transactional
    public void save(Customer customer) {
        em.persist(customer);
    }

    @Transactional
    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = findById(id);
        if (customer != null) {
            em.remove(customer);
        }
    }
}
