package org.example.repository;

import org.example.model.OrderDetail;
import org.example.repository.interfaces.OrderDetailRepository;

import java.util.ArrayList;
import java.util.UUID;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    private static OrderDetailRepository orderDetailRepository;
    private ArrayList<OrderDetail> orderDetails;

    private OrderDetailRepositoryImpl() {
        this.orderDetails = new ArrayList<>();
    }

    public static OrderDetailRepository getInstance() {
        if (orderDetailRepository == null) {
            orderDetailRepository = new OrderDetailRepositoryImpl();
        }
        return orderDetailRepository;
    }

    @Override
    public OrderDetail getById(UUID uuid) {
        return null;
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return false;
    }

    @Override
    public boolean add(OrderDetail orderDetail) {
        return false;
    }

    @Override
    public boolean update(OrderDetail orderDetail) {
        return false;
    }
}
