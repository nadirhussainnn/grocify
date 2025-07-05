// Service: CustomerService.java
package sst.swam.grocify.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import sst.swam.grocify.dao.CustomerDao;
import sst.swam.grocify.model.Customer;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerDao customerDao;

    public Customer findById(Long id) {
        return customerDao.findById(id);
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public void save(Customer customer) {
        customerDao.save(customer);
    }

    public Customer update(Customer customer) {
        return customerDao.update(customer);
    }

    public void delete(Long id) {
        customerDao.delete(id);
    }
}
