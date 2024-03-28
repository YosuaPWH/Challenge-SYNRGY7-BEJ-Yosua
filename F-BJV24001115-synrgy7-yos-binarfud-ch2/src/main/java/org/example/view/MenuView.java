package org.example.view;

import lombok.AllArgsConstructor;
import org.example.controller.PesananController;
import org.example.model.Food;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@AllArgsConstructor
public class MenuView {

    PesananController pesananController;
    Scanner scn;

    public void displayMenu() {
        System.out.println();
        System.out.println("=".repeat(30));
        System.out.println("Selamat datang di BinarFud");
        System.out.println("=".repeat(30));
        System.out.println("Silahkan pilih makanan :");
        for (Food food : pesananController.getMenu()) {
            System.out.printf("%d. %-20s | %.10s%n", food.getId(), food.getName(), food.getPrice());
        }
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.print("=> ");
        int inputUser = scn.nextInt();
        if (inputUser == 99) {
            if (pesananController.getPesanan().isEmpty()) {
                System.out.println("Anda belum memesan apapun, silahkan pilih makanan terlebih dahulu!");
                displayMenu();
            } else {
                konfirmasiPembayaran();
            }
        } else if (inputUser == 0) {
            scn.close();
            System.exit(0);
        } else if (pesananController.getFoodById(inputUser) != null) {
            konfirmasiPesanan(inputUser);
        } else {
            wrongErrorInput();
        }
    }

    public void konfirmasiPesanan(int idFood) {
        Food food = pesananController.getFoodById(idFood);
        System.out.println();
        System.out.println("=".repeat(30));
        System.out.println("Berapa pesanan anda");
        System.out.println("=".repeat(30));
        System.out.println(food.getName() + " | " + food.getPrice());
        System.out.println("input 0 untuk kembali");
        System.out.print("qty => ");

        int inputUser = scn.nextInt();
        if (inputUser == 0) {
            displayMenu();
        } else if (inputUser > 0) {
            System.out.println("Mesan " + food.getName() + " sebanyak " + inputUser + "\ndengan total harga " + (food.getPrice() * inputUser));
            if (pesananController.getPesanan().containsKey(inputUser)) {
                pesananController.getPesanan().replace(idFood, pesananController.getPesanan().get(idFood) + inputUser);
            } else {
                pesananController.getPesanan().put(idFood, inputUser);
            }
            displayMenu();
        } else {
            wrongMessageErrorInput(idFood);
        }
    }

    public void wrongMessageErrorInput(int idFood) {
        System.out.println("\n" + "=".repeat(20) + "          " + "=".repeat(20));
        System.out.printf("%-29s %-20s", "Mohon masukkan input", "Minimal 1 jumlah");
        System.out.printf("%-30s %-20s", "\npilihan anda", "pesanan!");
        System.out.println("\n" + "=".repeat(20) + "          " + "=".repeat(20));
        System.out.println("(Y/y) untuk lanjut");
        System.out.println("(N/n) untuk kembali ke menu utama");
        System.out.print("=> ");
        String inputUser = scn.next();
        if (inputUser.equalsIgnoreCase("y")) {
            konfirmasiPesanan(idFood);
        } else if (inputUser.equalsIgnoreCase("n")) {
            displayMenu();
        }
    }


    public void konfirmasiPembayaran() {
        System.out.println();
        System.out.println("=".repeat(30));
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("=".repeat(30));
        System.out.println(pesananUser());

        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("=> ");
        int inputUser = scn.nextInt();
        if (inputUser == 1) {
            System.out.println();
            cetakStruk();
        } else if (inputUser == 2) {
            displayMenu();
        } else if (inputUser == 0) {
            scn.close();
            System.exit(0);
        } else {
            wrongErrorInput();
        }
    }

    public void wrongErrorInput() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("Inputan anda salah");
        System.out.println("silahkan coba kembali!");
        System.out.println("=".repeat(30));
        System.out.println("(Y/y) untuk lanjut");
        System.out.println("(N/n) untuk keluar");
        System.out.print("=> ");
        String inputUser = scn.next();
        if (inputUser.equalsIgnoreCase("y")) {
            displayMenu();
        } else if (inputUser.equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }

    public String pesananUser() {
        int jumlahTotalHarga = 0;
        int jumlahPesanan = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int id : pesananController.getPesanan().keySet()) {
            Food food = pesananController.getFoodById(id);
            int qty = pesananController.getPesanan().get(id);
            int total = food.getPrice() * qty;
            jumlahTotalHarga += total;
            jumlahPesanan += qty;
            stringBuilder.append(String.format("%-15s %2d %10d%n", food.getName(), qty, total));
        }
        stringBuilder.append(String.format("%s", "-".repeat(30) + "\n"));
        stringBuilder.append(String.format("%-15s %2d %10d", "Total", jumlahPesanan, jumlahTotalHarga));

        return stringBuilder.toString();
    }

    public void cetakStruk() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("struk.txt"), StandardCharsets.UTF_8
                )
            )
        ) {

            bufferedWriter.write("=".repeat(30) + "\n");
            bufferedWriter.write("BinarFud\n");
            bufferedWriter.write("=".repeat(30) + "\n");
            bufferedWriter.write("Terima kasih sudah memesan\n");
            bufferedWriter.write("di BinarFud\n");
            bufferedWriter.write("\n");
            bufferedWriter.write("Dibawah ini adalah pesanan anda\n");
            bufferedWriter.write(pesananUser());
            bufferedWriter.write("\n");
            bufferedWriter.write("Pembayaran : BinarCash\n");
            bufferedWriter.write("\n");
            bufferedWriter.write("=".repeat(30) + "\n");
            bufferedWriter.write("Simpan struk ini sebagai\n");
            bufferedWriter.write("bukti pembayaran\n");
            bufferedWriter.write("=".repeat(30) + "\n");

        } catch (IOException e) {
            System.out.println("Error saat membuat file struk: " + e.getMessage());
        } finally {
            scn.close();
        }
        System.out.println("Pembayaran berhasil, struk telah dicetak!");
    }

}
