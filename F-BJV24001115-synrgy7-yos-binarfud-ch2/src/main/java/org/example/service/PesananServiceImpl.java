package org.example.service;

import lombok.Getter;
import org.example.model.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class PesananServiceImpl implements PesananService {

    private final List<Food> menu = new ArrayList<>();

    @Getter
    private final HashMap<Integer, Integer> pesanan = new HashMap<>();

    public PesananServiceImpl() {
        menu.add(new Food(1, "Nasi Goreng", 15_000));
        menu.add(new Food(2, "Mie Goreng", 13_000));
        menu.add(new Food(3, "Nasi + Ayam", 18_000));
        menu.add(new Food(4, "Es Teh Manis", 3_000));
        menu.add(new Food(5, "Es Jeruk", 5_000));
    }

    @Override
    public Food getFoodById(int id) {
        return getMenu().stream().filter(food -> food.getId() == id).findFirst().orElse(null);
    }
}
