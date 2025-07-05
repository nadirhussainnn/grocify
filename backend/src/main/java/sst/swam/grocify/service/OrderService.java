// Service: OrderService.java
package sst.swam.grocify.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import sst.swam.grocify.dao.OrderDao;
import sst.swam.grocify.model.Order;
import sst.swam.grocify.model.Customer;

import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderDao orderDao;

    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    public List<Order> findByCustomer(Customer customer) {
        return orderDao.findByCustomer(customer);
    }

    public void save(Order order) {
        orderDao.save(order);
    }

    public Order update(Order order) {
        return orderDao.update(order);
    }

    public void delete(Long id) {
        orderDao.delete(id);
    }
}