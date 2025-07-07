package sst.swam.grocify.mapper;

import sst.swam.grocify.dto.CustomerDTO;
import sst.swam.grocify.model.Customer;

public class CustomerMapper {
	
//	This mapper will return orders as well
    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setDateJoined(customer.getCreatedAt());
        return dto;
    }
    
//    For just list of customers, we don't include orders
    public static CustomerDTO toDTOWithoutOrders(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setDateJoined(customer.getCreatedAt());
        dto.setOrders(null);
        return dto;
    }
}