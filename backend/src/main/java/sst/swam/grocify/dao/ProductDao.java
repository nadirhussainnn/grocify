package sst.swam.grocify.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

	public List<Product> findAllFiltered(int page, int limit, String search, Boolean isCustomer) {
	    String baseQuery = "SELECT p FROM Product p";
	    StringBuilder whereClause = new StringBuilder();

	    if (search != null && !search.isBlank()) {
	        whereClause.append(" WHERE LOWER(p.name) LIKE :search OR LOWER(p.description) LIKE :search");
	    }

	    if (Boolean.TRUE.equals(isCustomer)) {
	        if (whereClause.length() == 0) {
	            whereClause.append(" WHERE p.quantity > 0");
	        } else {
	            whereClause.append(" AND p.quantity > 0");
	        }
	    }

	    TypedQuery<Product> query = em.createQuery(baseQuery + whereClause + " ORDER BY p.id DESC", Product.class);

	    if (search != null && !search.isBlank()) {
	        query.setParameter("search", "%" + search.toLowerCase() + "%");
	    }

	    return query.setFirstResult(page * limit).setMaxResults(limit).getResultList();
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