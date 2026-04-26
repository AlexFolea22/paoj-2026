package com.pao.project.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int id;
    private String nume;
    private Adresa adresa;
    private double rating;
    private Meniu meniu = new Meniu();
    private List<Review> reviewuri = new ArrayList<>();

    public Restaurant(int id, String nume, Adresa adresa, double rating) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public double getRating() {
        return rating;
    }

    public Meniu getMeniu() {
        return meniu;
    }

    public List<Review> getReviewuri() {
        return reviewuri;
    }

    public void adaugaReview(Review review) {
        reviewuri.add(review);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa=" + adresa +
                ", rating=" + rating +
                '}';
    }
}
