package sst.swam.grocify.mapper;

import sst.swam.grocify.dto.OrderItemDTO;
import sst.swam.grocify.model.OrderItem;
import sst.swam.grocify.model.Product;

public class OrderItemMapper {
    public static OrderItem toEntity(OrderItemDTO dto, Product product) {
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPriceAtPurchase(product.getPrice());
        return item;
    }

    public static OrderItemDTO toDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductDescription(item.getProduct().getDescription());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
