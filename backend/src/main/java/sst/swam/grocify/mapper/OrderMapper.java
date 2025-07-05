package sst.swam.grocify.mapper;

import sst.swam.grocify.model.Order;
import sst.swam.grocify.model.OrderItem;
import sst.swam.grocify.dto.OrderDTO;
import sst.swam.grocify.dto.OrderItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());
        List<OrderItemDTO> items = order.getItems().stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toList());
        dto.setItems(items);
        return dto;
    }
}
