package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sst.swam.grocify.model.Product;

import java.util.List;

@ApplicationScoped
public class ProductDao {

    @PersistenceContext
    EntityManager em;

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Transactional
    public void save(Product product) {
        em.persist(product);
    }

    @Transactional
    public Product update(Product product) {
        return em.merge(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = findById(id);
        if (product != null) {
            em.remove(product);
        }
    }
}