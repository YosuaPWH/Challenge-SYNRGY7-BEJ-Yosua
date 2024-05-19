package org.example;

import org.example.controller.ViewController;
import org.example.model.Product;
import org.example.service.*;
import org.example.service.interfaces.*;

import java.util.List;
import java.util.Scanner;

public class App {

//    private MerchantService merchantService;
    private UserService userService;
    private OrderService orderService;
    private OrderDetailService orderDetailService;
    private ProductService productService;

    public void startApp() {
        initClass();

        List<Product> products = productService.getAll();

        Scanner scn = new Scanner(System.in);

        ViewController.displayMenu(products);
        int choice = scn.nextInt();
//        if ()

    }

    void initClass() {
        userService = UserServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        orderDetailService = OrderDetailServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }
}
