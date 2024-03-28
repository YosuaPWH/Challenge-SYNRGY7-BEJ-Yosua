package org.example.service;

import org.example.model.Food;

import java.util.HashMap;
import java.util.List;

public interface PesananService {

    List<Food> getMenu();

    Food getFoodById(int id);

    HashMap<Integer, Integer> getPesanan();

}
