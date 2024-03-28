package org.yosua;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu {
    private final List<Food> menu = new ArrayList<>();
    private final HashMap<Integer, Integer> pesanan = new HashMap<>();
    public Menu() {
        initMenu();
    }

    public void initMenu() {
        menu.add(new Food(1, "Nasi Goreng", 15_000));
        menu.add(new Food(2, "Mie Goreng", 13_000));
        menu.add(new Food(3, "Nasi + Ayam", 18_000));
        menu.add(new Food(4, "Es Teh Manis", 3_000));
        menu.add(new Food(5, "Es Jeruk", 5_000));
    }

    public List<Food> getMenu() {
        return menu;
    }

    public HashMap<Integer, Integer> getPesanan() {
        return pesanan;
    }

    public Food getFoodById(int id) {
        return menu.stream().filter(food -> food.getId() == id).findFirst().orElse(null);
    }


}
