package com.pao.project.fooddelivery.model;

public class Review {
    private int id;
    private Client client;
    private Restaurant restaurant;
    private int nota;
    private String comentariu;

    public Review(int id, Client client, Restaurant restaurant, int nota, String comentariu) {
        this.id = id;
        this.client = client;
        this.restaurant = restaurant;
        this.nota = nota;
        this.comentariu = comentariu;
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public int getNota() {
        return nota;
    }

    public String getComentariu() {
        return comentariu;
    }

    @Override
    public String toString() {
        return "Review{" +
                "client=" + client.getNume() +
                ", restaurant=" + restaurant.getNume() +
                ", nota=" + nota +
                ", comentariu='" + comentariu + '\'' +
                '}';
    }
}
