package sst.swam.grocify.mapper;

import sst.swam.grocify.dto.OrderDTO;
import sst.swam.grocify.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus().name());
        dto.setItems(order.getItems().stream()
                         .map(OrderItemMapper::toDTO)
                         .collect(Collectors.toList()));
        return dto;
    }
} 