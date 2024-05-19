package org.example.repository;

import org.example.model.Order;
import org.example.repository.interfaces.OrderRepository;

import java.util.ArrayList;
import java.util.UUID;

public class OrderRepositoryImpl implements OrderRepository {
    private static OrderRepository orderRepository;
    private ArrayList<Order> orders;

    private OrderRepositoryImpl() {
        this.orders = new ArrayList<>();
    }

    public static OrderRepository getInstance() {
        if (orderRepository == null) {
            orderRepository = new OrderRepositoryImpl();
        }
        return orderRepository;
    }

    @Override
    public boolean add(Order order) {
        if (order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        return orders.add(order);
    }

}
