package com.pao.project.fooddelivery.model;

public class LinieComanda {
    private Produs produs;
    private int cantitate;

    public LinieComanda(Produs produs, int cantitate) {
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public Produs getProdus() {
        return produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public double calculeazaSubtotal() {
        return produs.getPret() * cantitate;
    }

    @Override
    public String toString() {
        return cantitate + " x " + produs.getNume() + " = " + calculeazaSubtotal();
    }
}
