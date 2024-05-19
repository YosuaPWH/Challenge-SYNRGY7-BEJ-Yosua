package org.example;

import org.example.service.MerchantServiceImpl;
import org.example.service.OrderServiceImpl;
import org.example.service.UserServiceImpl;
import org.example.service.interfaces.*;

public class App {

    private MerchantService merchantService;
    private UserService userService;
    private OrderService orderService;
    private OrderDetailService orderDetailService;
    private ProductService productService;

    public void startApp() {
        merchantService = MerchantServiceImpl.getInstance();
        userService = UserServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        orderDetailService = OrderDetailServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }
}
