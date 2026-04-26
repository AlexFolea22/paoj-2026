package com.pao.project.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private int id;
    private Client client;
    private Restaurant restaurant;
    private Sofer sofer;
    private List<LinieComanda> linii = new ArrayList<>();
    private StatusComanda status = StatusComanda.PLASATA;
    private Plata plata;

    public Comanda(int id, Client client, Restaurant restaurant) {
        this.id = id;
        this.client = client;
        this.restaurant = restaurant;
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

    public Sofer getSofer() {
        return sofer;
    }

    public void setSofer(Sofer sofer) {
        this.sofer = sofer;
    }

    public StatusComanda getStatus() {
        return status;
    }

    public void setStatus(StatusComanda status) {
        this.status = status;
    }

    public Plata getPlata() {
        return plata;
    }

    public void setPlata(Plata plata) {
        this.plata = plata;
    }

    public void adaugaLinie(LinieComanda linie) {
        linii.add(linie);
    }

    public double calculeazaTotal() {
        double total = 0;
        for (LinieComanda linie : linii) {
            total += linie.calculeazaSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", client=" + client.getNume() + " " + client.getPrenume() +
                ", restaurant=" + restaurant.getNume() +
                ", sofer=" + (sofer == null ? "neasignat" : sofer.getNume()) +
                ", status=" + status +
                ", total=" + calculeazaTotal() +
                '}';
    }
}
