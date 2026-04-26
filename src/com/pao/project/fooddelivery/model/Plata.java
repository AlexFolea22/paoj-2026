package com.pao.project.fooddelivery.model;

public class Plata {
    private int id;
    private double suma;
    private String metoda;
    private boolean confirmata;

    public Plata(int id, double suma, String metoda, boolean confirmata) {
        this.id = id;
        this.suma = suma;
        this.metoda = metoda;
        this.confirmata = confirmata;
    }

    public int getId() {
        return id;
    }

    public double getSuma() {
        return suma;
    }

    public String getMetoda() {
        return metoda;
    }

    public boolean isConfirmata() {
        return confirmata;
    }
}
