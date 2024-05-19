package org.example.repository;

import org.example.model.OrderDetail;
import org.example.repository.interfaces.OrderDetailRepository;

import java.util.ArrayList;
import java.util.Optional;
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
    public Optional<OrderDetail> getById(UUID uuid) {
        return orderDetails.stream()
                .filter(orderDetail -> orderDetail.getId().equals(uuid))
                .findFirst();
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return orderDetails.removeIf(orderDetail -> orderDetail.getId().equals(uuid));
    }

    @Override
    public boolean add(OrderDetail orderDetail) {
        return orderDetails.add(orderDetail);
    }

    @Override
    public boolean update(OrderDetail orderDetail) {
        return false;
    }
}
