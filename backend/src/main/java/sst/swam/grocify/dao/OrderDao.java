package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import sst.swam.grocify.model.Order;
import sst.swam.grocify.model.Customer;
import sst.swam.grocify.model.Order.OrderStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public List<Order> findAllFiltered(int page, int limit, String status, String date) {
        StringBuilder queryStr = new StringBuilder("SELECT DISTINCT o FROM Order o JOIN FETCH o.items WHERE 1=1");

        if (status != null && !status.isBlank()) {
            queryStr.append(" AND o.status = :status");
        }
        if (date != null && !date.isBlank()) {
            queryStr.append(" AND FUNCTION('DATE', o.orderDate) = :orderDate");
        }

        queryStr.append(" ORDER BY o.orderDate DESC");
        TypedQuery<Order> query = em.createQuery(queryStr.toString(), Order.class);

        if (status != null && !status.isBlank()) {
            query.setParameter("status", OrderStatus.valueOf(status));
        }
        if (date != null && !date.isBlank()) {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            query.setParameter("orderDate", java.sql.Date.valueOf(parsedDate));
        }

        return query.setFirstResult(page * limit)
                    .setMaxResults(limit)
                    .getResultList();
    }

    public List<Order> findByCustomerFiltered(Customer customer, int page, int limit, String status, String date) {

        StringBuilder queryStr = new StringBuilder(
        		  "SELECT DISTINCT o FROM Order o JOIN FETCH o.items WHERE o.customer = :customer"
        		);

        if (status != null && !status.isBlank()) {
            queryStr.append(" AND o.status = :status");
        }
        if (date != null && !date.isBlank()) {
            queryStr.append(" AND FUNCTION('DATE', o.orderDate) = :orderDate");
        }

        queryStr.append(" ORDER BY o.orderDate DESC");
        TypedQuery<Order> query = em.createQuery(queryStr.toString(), Order.class)
            .setParameter("customer", customer);

        if (status != null && !status.isBlank()) {
            query.setParameter("status", OrderStatus.valueOf(status));
        }
        if (date != null && !date.isBlank()) {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            query.setParameter("orderDate", java.sql.Date.valueOf(parsedDate));
        }

        return query.setFirstResult(page * limit).setMaxResults(limit).getResultList();
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