package org.example;

import org.example.controller.PesananController;
import org.example.service.PesananService;
import org.example.service.PesananServiceImpl;
import org.example.view.MenuView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PesananService pesananService = new PesananServiceImpl();
        PesananController pesananController = new PesananController(pesananService);
        Scanner scn = new Scanner(System.in);

        MenuView menuView = new MenuView(pesananController, scn);
        menuView.displayMenu();
    }
}