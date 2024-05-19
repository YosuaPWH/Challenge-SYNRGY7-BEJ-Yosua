package org.example.repository.interfaces;

import org.example.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    boolean add(Order order);
    boolean update(Order order);
    boolean deleteById(UUID uuid);
    boolean deleteByUserId(UUID userId);
    Optional<Order> getById(UUID uuid);
    List<Order> getAllByUserId(UUID userId);
}
