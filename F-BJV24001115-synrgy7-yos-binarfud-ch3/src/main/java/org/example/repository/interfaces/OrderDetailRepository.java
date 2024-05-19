package org.example.repository.interfaces;

import org.example.model.OrderDetail;

import java.util.UUID;

public interface OrderDetailRepository {
    OrderDetail getById(UUID uuid);
    boolean deleteById(UUID uuid);
    boolean add(OrderDetail orderDetail);
    boolean update(OrderDetail orderDetail);
}
