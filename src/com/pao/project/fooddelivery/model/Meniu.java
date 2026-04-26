package com.pao.project.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Meniu {
    private List<Produs> produse = new ArrayList<>();

    public void adaugaProdus(Produs produs) {
        produse.add(produs);
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public Produs cautaProdusDupaId(int id) {
        for (Produs produs : produse) {
            if (produs.getId() == id) {
                return produs;
            }
        }
        return null;
    }
}
