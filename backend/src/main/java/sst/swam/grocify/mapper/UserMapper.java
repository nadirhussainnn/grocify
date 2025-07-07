package sst.swam.grocify.mapper;

import sst.swam.grocify.model.Admin;
import sst.swam.grocify.model.Customer;
import sst.swam.grocify.dto.AdminDTO;
import sst.swam.grocify.dto.CustomerDTO;

public class UserMapper {

    public static AdminDTO toAdminDTO(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setEmail(admin.getEmail());
        dto.setDateJoined(admin.getDateJoined());
        return dto;
    }

    public static CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setDateJoined(customer.getDateJoined());
        return dto;
    }
}