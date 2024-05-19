package org.example.service;

import org.example.repository.OrderDetailRepositoryImpl;
import org.example.repository.interfaces.OrderDetailRepository;
import org.example.service.interfaces.OrderDetailService;

public class OrderDetailServiceImpl implements OrderDetailService {

    private static OrderDetailServiceImpl orderDetailService;
    private OrderDetailRepository orderDetailRepository;

    private OrderDetailServiceImpl() {
        orderDetailRepository = OrderDetailRepositoryImpl.getInstance();
    }

    public static OrderDetailServiceImpl getInstance() {
        if (orderDetailService == null) {
            orderDetailService = new OrderDetailServiceImpl();
        }
        return orderDetailService;
    }
}
