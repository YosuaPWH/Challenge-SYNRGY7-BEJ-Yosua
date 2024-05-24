package org.example.service;

import org.example.model.Order;
import org.example.model.User;
import org.example.model.dto.OrderRequest;
import org.example.service.interfaces.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



class OrderServiceImplTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Test
    void testAddOrder_Success() {
        User user = new User(UUID.randomUUID(), "test", "test", "test");
        OrderRequest orderRequest = new OrderRequest(user, UUID.randomUUID(), 1, "test");
        UUID orderId = orderService.add(orderRequest);

        Optional<Order> order = orderService.findById(orderId);
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(orderRequest.getAddress(), order.get().getAddress());
        Assertions.assertEquals(orderRequest.getUser(), order.get().getUser());
    }

    @Test
    void testFindByInvalidId_Failed() {
        UUID invalidId = UUID.randomUUID();
        Optional<Order> order = orderService.findById(invalidId);
        Assertions.assertFalse(order.isPresent());
    }

    @Test
    void testDeleteById_Success() {
        User user = new User(UUID.randomUUID(), "test", "test", "test");
        OrderRequest orderRequest = new OrderRequest(user, UUID.randomUUID(), 1, "test");
        UUID orderId = orderService.add(orderRequest);
        Assertions.assertTrue(orderService.isExist(orderId));

        boolean isDeleted = orderService.deleteById(orderId);
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(orderService.isExist(orderId));
    }

    @Test
    void testGetAllByUserId_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test", "test", "test");
        orderService.add(new OrderRequest(user, UUID.randomUUID(), 1, "123 Oak Ave"));
        orderService.add(new OrderRequest(user, UUID.randomUUID(), 5, "123 Oak Avening"));

        List<Order> orders = orderService.getAll(userId);
        Assertions.assertEquals(2, orders.size());
        orders.forEach(order -> Assertions.assertEquals(userId, order.getUser().getId()));
    }


    @Test
    void testUpdateById_Success() {
        User user = new User(UUID.randomUUID(), "test", "test", "test");
        OrderRequest orderRequest = new OrderRequest(user, UUID.randomUUID(), 1, "123 Oak Ave");
        UUID orderId = orderService.add(orderRequest);

        Order updatedOrder = new Order(orderId, null, "456 Oak Ave", orderRequest.getUser(), true);
        boolean isUpdated = orderService.updateById(orderId, updatedOrder);
        Assertions.assertTrue(isUpdated);

        Optional<Order> order = orderService.findById(orderId);
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(updatedOrder.getAddress(), order.get().getAddress());
        Assertions.assertEquals(updatedOrder.getCompleted(), order.get().getCompleted());
    }

    @Test
    void testUpdateByInvalidId_Failed() {
        User user = new User(UUID.randomUUID(), "test", "test", "test");
        UUID invalidId = UUID.randomUUID();
        Order updatedOrder = new Order(invalidId, null, "456 Oak Ave", user, true);
        boolean isUpdated = orderService.updateById(invalidId, updatedOrder);
        Assertions.assertFalse(isUpdated);
    }

}