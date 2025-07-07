package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    public List<Customer> findAll(int page, int limit, String search) {
        String baseQuery = "SELECT c FROM Customer c";
        StringBuilder whereClause = new StringBuilder();

        boolean hasSearch = search != null && !search.isBlank();

        if (hasSearch) {
            whereClause.append(" WHERE LOWER(c.email) LIKE :search");
        }

        TypedQuery<Customer> query = em.createQuery(baseQuery + whereClause + " ORDER BY c.id DESC", Customer.class);

        if (hasSearch) {
            query.setParameter("search", "%" + search.toLowerCase() + "%");
        }

        return query.setFirstResult(page * limit)
                    .setMaxResults(limit)
                    .getResultList();
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
