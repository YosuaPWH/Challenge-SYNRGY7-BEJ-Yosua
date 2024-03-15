package org.yosua;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    private static final Menu listFood = new Menu();
    private static final Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        displayMenu();
    }

    public static void displayMenu() {
        System.out.println();
        System.out.println("==========================");
        System.out.println("Selamat datang di BinarFud");
        System.out.println("==========================");
        System.out.println("Silahkan pilih makanan :");
        for (Food food : listFood.getMenu()) {
            System.out.printf("%d. %-20s | %.10s\n", food.getId(), food.getName(), food.getPrice());
        }
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.println("=> ");
        int inputUser = scn.nextInt();
        if (inputUser == 99) {
            if (listFood.getPesanan().isEmpty()) {
                System.out.println("Anda belum memesan apapun, silahkan pilih makanan terlebih dahulu!");
                displayMenu();
            } else {
                konfirmasiPembayaran();
            }
        } else if (inputUser == 0) {
            return;
        } else if (listFood.getFoodById(inputUser) != null) {
            konfirmasiPesanan(inputUser);
        } else {
            System.out.println("Pesanan anda tidak ditemukan, silahkan coba kembali!");
            displayMenu();
        }
    }

    public static void konfirmasiPesanan(int idFood) {
        Food food = listFood.getFoodById(idFood);
        System.out.println();
        System.out.println("==========================");
        System.out.println("Berapa pesanan anda");
        System.out.println("==========================");
        System.out.println(food.getName() + " | " + food.getPrice());
        System.out.println("input 0 untuk kembali");
        System.out.print("qty => ");

        int inputUser = scn.nextInt();
        if (inputUser == 0) {
            displayMenu();
        } else if (inputUser > 0) {
            System.out.println("Mesan " + food.getName() + " sebanyak " + inputUser + " dengan total harga " + (food.getPrice() * inputUser));
            if (listFood.getPesanan().containsKey(inputUser)) {
                listFood.getPesanan().replace(idFood, listFood.getPesanan().get(idFood) + inputUser);
            } else {
                listFood.getPesanan().put(idFood, inputUser);
            }
            displayMenu();
        } else {
            System.out.println("Inputan anda salah, silahkan coba kembali!");
            konfirmasiPesanan(idFood);
        }
    }

    public static void konfirmasiPembayaran() {
        System.out.println();
        System.out.println("==========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("==========================");
        System.out.println(pesananUser());

        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("3. Keluar aplikasi");
        System.out.print("=> ");
        int inputUser = scn.nextInt();
        if (inputUser == 1) {
            System.out.println();
            cetakStruk();
        } else if (inputUser == 2) {
            displayMenu();
        } else if (inputUser == 3) {
            return;
        }
    }

    public static void cetakStruk() {
        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("struk.txt"), StandardCharsets.UTF_8
                    )
            );

            writer.write("==========================\n");
            writer.write("BinarFud\n");
            writer.write("==========================\n");
            writer.write("Terima kasih sudah memesan\n");
            writer.write("di BinarFud\n");
            writer.write("\n");
            writer.write("Dibawah ini adalah pesanan anda\n");
            writer.write(pesananUser());
            writer.write("\n");
            writer.write("Pembayaran : BinarCash\n");
            writer.write("\n");
            writer.write("==========================\n");
            writer.write("Simpan struk ini sebagai\n");
            writer.write("bukti pembayaran\n");
            writer.write("==========================\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saat membuat file struk: " + e.getMessage());
        }
        System.out.println("Pembayaran berhasil, struk telah dicetak!");
    }

    public static String pesananUser() {
        int jumlahTotalHarga = 0;
        int jumlahPesanan = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int id : listFood.getPesanan().keySet()) {
            Food food = listFood.getFoodById(id);
            int qty = listFood.getPesanan().get(id);
            int total = food.getPrice() * qty;
            jumlahTotalHarga += total;
            jumlahPesanan += qty;
            stringBuilder.append(String.format("%-15s %2d %10d\n", food.getName(), qty, total));
        }
        stringBuilder.append(String.format("%s", "-----------------------------------+\n"));
        stringBuilder.append(String.format("%-15s %2d %10d\n", "Total", jumlahPesanan, jumlahTotalHarga));

        return stringBuilder.toString();
    }
}