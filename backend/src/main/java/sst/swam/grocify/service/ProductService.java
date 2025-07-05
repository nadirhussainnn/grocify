package sst.swam.grocify.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import sst.swam.grocify.dao.ProductDao;
import sst.swam.grocify.model.Product;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductDao productDao;

    public Product findById(Long id) {
        return productDao.findById(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public Product update(Product product) {
        return productDao.update(product);
    }

    public void delete(Long id) {
        productDao.delete(id);
    }
}