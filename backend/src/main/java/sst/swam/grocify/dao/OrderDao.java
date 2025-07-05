package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sst.swam.grocify.model.Order;
import sst.swam.grocify.model.Customer;

import java.util.List;

@ApplicationScoped
public class OrderDao {

    @PersistenceContext
    EntityManager em;

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findByCustomer(Customer customer) {
        return em.createQuery("SELECT o FROM Order o WHERE o.customer = :customer", Order.class)
                 .setParameter("customer", customer)
                 .getResultList();
    }

    @Transactional
    public void save(Order order) {
        em.persist(order);
    }

    @Transactional
    public Order update(Order order) {
        return em.merge(order);
    }

    @Transactional
    public void delete(Long id) {
        Order order = findById(id);
        if (order != null) {
            em.remove(order);
        }
    }
}
