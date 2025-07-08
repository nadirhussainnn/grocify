package sst.swam.grocify.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import sst.swam.grocify.dao.OrderDao;
import sst.swam.grocify.dto.OrderDTO;
import sst.swam.grocify.dto.OrderItemDTO;
import sst.swam.grocify.mapper.OrderItemMapper;
import sst.swam.grocify.model.Customer;
import sst.swam.grocify.model.Order;
import sst.swam.grocify.model.Order.OrderStatus;
import sst.swam.grocify.model.OrderItem;
import sst.swam.grocify.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderDao orderDao;

    @Inject
    ProductService productService;

    public void placeOrder(Customer customer, OrderDTO dto) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO itemDTO : dto.getItems()) {
        	
        	Product product = productService.findById(itemDTO.getProductId());
            if (product.getQuantity() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            productService.update(product); 
            OrderItem item = OrderItemMapper.toEntity(itemDTO, product);
            item.setOrder(order);
            orderItems.add(item);
        }

        order.setItems(orderItems);
        orderDao.save(order);
    }

    public boolean markDelivered(Long id) {
        Order order = orderDao.findById(id);
        if (order == null) return false;
        order.setStatus(OrderStatus.DELIVERED);
        orderDao.update(order);
        return true;
    }

    public List<Order> findAllFiltered(int page, int limit, String status, String date) {
        return orderDao.findAllFiltered(page, limit, status, date);
    }
    
    public List<Order> findByCustomerFiltered(Customer customer, int page, int limit, String status, String date) {
        return orderDao.findByCustomerFiltered(customer, page, limit, status, date);
    }

}