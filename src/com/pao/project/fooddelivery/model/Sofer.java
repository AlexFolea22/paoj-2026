package com.pao.project.fooddelivery.model;

public class Sofer extends Persoana implements Comparable<Sofer> {
    private String numarMasina;
    private double rating;
    private boolean disponibil = true;

    public Sofer(int id, String nume, String prenume, String telefon, String numarMasina, double rating) {
        super(id, nume, prenume, telefon);
        this.numarMasina = numarMasina;
        this.rating = rating;
    }

    public String getNumarMasina() {
        return numarMasina;
    }

    public double getRating() {
        return rating;
    }

    public boolean isDisponibil() {
        return disponibil;
    }

    public void setDisponibil(boolean disponibil) {
        this.disponibil = disponibil;
    }

    @Override
    public String getRol() {
        return "Sofer";
    }

    @Override
    public int compareTo(Sofer other) {
        int ratingCompare = Double.compare(other.rating, rating);
        if (ratingCompare != 0) return ratingCompare;
        int numeCompare = nume.compareTo(other.nume);
        if (numeCompare != 0) return numeCompare;
        return Integer.compare(id, other.id);
    }

    @Override
    public String toString() {
        return "Sofer{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", numarMasina='" + numarMasina + '\'' +
                ", rating=" + rating +
                ", disponibil=" + disponibil +
                '}';
    }
}
