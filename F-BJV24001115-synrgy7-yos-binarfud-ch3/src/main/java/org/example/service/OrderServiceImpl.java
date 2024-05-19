package org.example.service;

import org.example.model.Order;
import org.example.model.User;
import org.example.model.dto.OrderRequest;
import org.example.repository.OrderRepositoryImpl;
import org.example.repository.UserRepositoryImpl;
import org.example.repository.interfaces.OrderRepository;
import org.example.repository.interfaces.UserRepository;
import org.example.service.interfaces.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private static OrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private OrderServiceImpl() {
        orderRepository = OrderRepositoryImpl.getInstance();
        userRepository = UserRepositoryImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    @Override
    public boolean add(OrderRequest orderRequest) {
        User user = userRepository.getById(orderRequest.getUser().getId());
        if (user == null) {
            return false;
        }


        return true;
    }

    @Override
    public Optional<Order> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return false;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public boolean isExist(UUID uuid) {
        return false;
    }

    @Override
    public boolean updateById(UUID uuid, Order order) {
        return false;
    }
}
