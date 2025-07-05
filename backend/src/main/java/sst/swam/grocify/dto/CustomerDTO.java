
package sst.swam.grocify.dto;

import java.util.List;

public class CustomerDTO extends UserDTO {
    private List<OrderDTO> orders;

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
