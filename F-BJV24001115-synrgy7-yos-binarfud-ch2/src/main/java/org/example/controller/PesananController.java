package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Food;
import org.example.service.PesananService;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PesananController {

    PesananService pesananService;

    public Food getFoodById(int id) {
        return pesananService.getFoodById(id);
    }

    public Map<Integer, Integer> getPesanan() {
        return pesananService.getPesanan();
    }

    public List<Food> getMenu() {
        return pesananService.getMenu();
    }
}
