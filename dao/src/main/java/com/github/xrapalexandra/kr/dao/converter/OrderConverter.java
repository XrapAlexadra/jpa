package com.github.xrapalexandra.kr.dao.converter;

import com.github.xrapalexandra.kr.dao.entity.OrderContentEntity;
import com.github.xrapalexandra.kr.dao.entity.OrderEntity;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.OrderContent;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderEntity toEntity(Order order) {
        if (order == null)
            return null;
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setUser(UserConverter.toEntity(order.getUser()));
        orderEntity.setStatus(order.getStatus());
        orderEntity.setOrderContentList(listToEntity(order.getContentList()));
        return orderEntity;
    }

    public static Order fromEntity(OrderEntity orderEntity){
        if (orderEntity == null)
            return null;
        final Order order = new Order(
                UserConverter.fromEntity(orderEntity.getUser()),
                listFromEntity(orderEntity.getOrderContentList()),
                orderEntity.getStatus()
        );
        order.setId(orderEntity.getId());
        return order;
    }

    private static List<OrderContentEntity> listToEntity(List<OrderContent> list){
        return list.stream()
                .map(OrderContentConverter::toEntity)
                .collect(Collectors.toList());
    }

    private static List<OrderContent> listFromEntity(List<OrderContentEntity> list){
        return list.stream()
                .map(OrderContentConverter::fromEntity)
                .collect(Collectors.toList());
    }

    public static class OrderContentConverter {

        public static OrderContentEntity toEntity(OrderContent content) {
            if (content == null)
                return null;
            final OrderContentEntity contentEntity = new OrderContentEntity();
            contentEntity.setId(content.getId());
            contentEntity.setProduct(ProductConverter.toEntity(content.getProduct()));
            contentEntity.setOrderQuantity(content.getOrderQuantity());

            return contentEntity;
        }

        public static OrderContent fromEntity(OrderContentEntity contentEntity) {
            if (contentEntity == null)
                return null;
            final OrderContent content = new OrderContent(
                    ProductConverter.fromEntity(contentEntity.getProduct()),
                    contentEntity.getOrderQuantity()
            );
            content.setId(contentEntity.getId());
            return content;
        }
    }
}
