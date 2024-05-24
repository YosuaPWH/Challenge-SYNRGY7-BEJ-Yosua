package org.example.controller;

import org.example.model.Order;
import org.example.model.Product;
import org.example.service.interfaces.OrderService;

import java.util.List;
import java.util.Scanner;

public class ViewController {

    public static void displayMenu(List<Product> products) {
        System.out.println();
        System.out.println("=".repeat(30));
        System.out.println("Selamat datang di BinarFud");
        System.out.println("=".repeat(30));
        System.out.println("Silahkan pilih makanan :");
        int count = 1;
        for (Product product : products) {
            System.out.printf("%d. %-20s | %.10s%n", count++, product.getName(), product.getPrice());
        }
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
    }

    public static void choiceMenu(int choice, Scanner scn, List<Product> products, OrderService orderService) {
//        if (choice == 99) {
//            if (orderService.getAll().isEmpty()) {
//                System.out.println("Anda belum memesan apapun, silahkan pilih makanan terlebih dahulu!");
//                displayMenu(products);
//            } else {
////                konfirmasiPembayaran();
//            }
//        } else if (choice == 0) {
//            System.exit(0);
//        } else if (choice <= products.size()) {
//            System.out.println("Anda memilih " + products.get(choice - 1).getName());
//        } else {
//            wrongErrorInput(scn, products);
//        }
    }

    public static void wrongErrorInput(Scanner scn, List<Product> products) {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("Inputan anda salah");
        System.out.println("silahkan coba kembali!");
        System.out.println("=".repeat(30));
        System.out.println("(Y/y) untuk lanjut");
        System.out.println("(N/n) untuk keluar");
        System.out.print("=> ");
        String inputUser = scn.next();
        if (inputUser.equalsIgnoreCase("y")) {
            displayMenu(products);
        } else if (inputUser.equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }

    public void konfirmasiPembayaran(Scanner scn, OrderService orderService) {
//        System.out.println();
//        System.out.println("=".repeat(30));
//        System.out.println("Konfirmasi & Pembayaran");
//        System.out.println("=".repeat(30));
//        System.out.println(orderService.getAll()pesananUser());
//
//        System.out.println("1. Konfirmasi dan Bayar");
//        System.out.println("2. Kembali ke menu utama");
//        System.out.println("0. Keluar aplikasi");
//        System.out.print("=> ");
//        int inputUser = scn.nextInt();
//        if (inputUser == 1) {
//            System.out.println();
//            cetakStruk();
//        } else if (inputUser == 2) {
//            displayMenu();
//        } else if (inputUser == 0) {
//            scn.close();
//            System.exit(0);
//        } else {
//            wrongErrorInput();
//        }
    }

//    public String pesananUser(List<Order> orders) {
//        int jumlahTotalHarga = 0;
//        int jumlahPesanan = 0;
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int id : pesananController.getPesanan().keySet()) {
//            Food food = pesananController.getFoodById(id);
//            int qty = pesananController.getPesanan().get(id);
//            int total = food.getPrice() * qty;
//            jumlahTotalHarga += total;
//            jumlahPesanan += qty;
//            stringBuilder.append(String.format("%-15s %2d %10d%n", food.getName(), qty, total));
//        }
//        stringBuilder.append(String.format("%s", "-".repeat(30) + "\n"));
//        stringBuilder.append(String.format("%-15s %2d %10d", "Total", jumlahPesanan, jumlahTotalHarga));
//
//        return stringBuilder.toString();
//    }
}
