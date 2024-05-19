package org.example.service.interfaces;

import org.example.model.Order;
import org.example.model.dto.OrderRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    boolean add(OrderRequest orderRequest);
    Optional<Order> findById(UUID uuid);
    boolean deleteById(UUID uuid);
    List<Order> getAll();
    boolean isExist(UUID uuid);
    boolean updateById(UUID uuid, Order order);
}
