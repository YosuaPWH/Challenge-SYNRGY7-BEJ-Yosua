package org.example.repository;

import org.example.model.Order;
import org.example.repository.interfaces.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        if (order.getUuid() == null) {
            order.setUuid(UUID.randomUUID());
        }
        return orders.add(order);
    }

    @Override
    public boolean update(Order order) {
        // replace orders based on order id
        Optional<Order> existingOrder = getById(order.getUuid());

        if (existingOrder.isEmpty()) {
            return false;
        }
        orders.set(orders.indexOf(existingOrder.get()), order);
        return true;
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return orders.removeIf(order -> order.getUuid().equals(uuid));
    }

    @Override
    public boolean deleteByUserId(UUID userId) {
        return orders.removeIf(order -> order.getUser().getId().equals(userId));
    }

    @Override
    public Optional<Order> getById(UUID uuid) {
        return orders.stream()
                .filter(order -> order.getUuid().equals(uuid))
                .findFirst();
    }

    @Override
    public List<Order> getAllByUserId(UUID userId) {
        return orders.stream()
                .filter(order -> order.getUser().getId().equals(userId))
                .toList();
    }
}
