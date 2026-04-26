package com.pao.project.fooddelivery.model;

public final class Adresa {
    private final String oras;
    private final String strada;
    private final int numar;

    public Adresa(String oras, String strada, int numar) {
        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public String getOras() {
        return oras;
    }

    public String getStrada() {
        return strada;
    }

    public int getNumar() {
        return numar;
    }

    @Override
    public String toString() {
        return oras + ", " + strada + " nr. " + numar;
    }
}
