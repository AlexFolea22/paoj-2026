package com.pao.project.fooddelivery.service;

import com.pao.project.fooddelivery.exception.ComandaInvalidaException;
import com.pao.project.fooddelivery.exception.RestaurantNotFoundException;
import com.pao.project.fooddelivery.exception.UserNotFoundException;
import com.pao.project.fooddelivery.model.Comanda;
import com.pao.project.fooddelivery.model.LinieComanda;
import com.pao.project.fooddelivery.model.Plata;
import com.pao.project.fooddelivery.model.Produs;
import com.pao.project.fooddelivery.model.Restaurant;
import com.pao.project.fooddelivery.model.Review;
import com.pao.project.fooddelivery.model.Sofer;
import com.pao.project.fooddelivery.model.StatusComanda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDeliveryService {
    private static FoodDeliveryService instance;

    private List<Restaurant> restaurante = new ArrayList<>();
    private Map<Integer, Comanda> comenzi = new HashMap<>();
    private int nextComandaId = 1;
    private int nextPlataId = 1;

    private FoodDeliveryService() {
    }

    public static FoodDeliveryService getInstance() {
        if (instance == null) {
            instance = new FoodDeliveryService();
        }
        return instance;
    }

    public void adaugaRestaurant(Restaurant restaurant) {
        restaurante.add(restaurant);
    }

    public void stergeRestaurant(int id) throws RestaurantNotFoundException {
        restaurante.remove(cautaRestaurantDupaId(id));
    }

    public Restaurant cautaRestaurantDupaId(int id) throws RestaurantNotFoundException {
        for (Restaurant restaurant : restaurante) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }
        throw new RestaurantNotFoundException("Restaurantul cu id " + id + " nu exista.");
    }

    public void listeazaRestaurante() {
        for (Restaurant restaurant : restaurante) {
            System.out.println(restaurant);
        }
    }

    public List<Restaurant> cautaRestauranteDupaOras(String oras) {
        List<Restaurant> rezultat = new ArrayList<>();
        for (Restaurant restaurant : restaurante) {
            if (restaurant.getAdresa().getOras().equalsIgnoreCase(oras)) {
                rezultat.add(restaurant);
            }
        }
        return rezultat;
    }

    public void adaugaProdusInMeniu(int restaurantId, Produs produs) throws RestaurantNotFoundException {
        cautaRestaurantDupaId(restaurantId).getMeniu().adaugaProdus(produs);
    }

    public void listeazaMeniuRestaurant(int restaurantId) throws RestaurantNotFoundException {
        for (Produs produs : cautaRestaurantDupaId(restaurantId).getMeniu().getProduse()) {
            System.out.println(produs);
        }
    }

    public Comanda plaseazaComanda(int clientId, int restaurantId, int[] produseIds, int[] cantitati)
            throws RestaurantNotFoundException, UserNotFoundException, ComandaInvalidaException {
        if (produseIds.length != cantitati.length) {
            throw new ComandaInvalidaException("Numarul de produse nu corespunde cu numarul de cantitati.");
        }

        Restaurant restaurant = cautaRestaurantDupaId(restaurantId);
        Comanda comanda = new Comanda(nextComandaId++,
                UserService.getInstance().cautaClientDupaId(clientId), restaurant);

        for (int i = 0; i < produseIds.length; i++) {
            Produs produs = restaurant.getMeniu().cautaProdusDupaId(produseIds[i]);
            if (produs == null) {
                throw new ComandaInvalidaException("Produsul cu id " + produseIds[i] + " nu exista in meniu.");
            }
            comanda.adaugaLinie(new LinieComanda(produs, cantitati[i]));
        }

        comanda.setPlata(new Plata(nextPlataId++, comanda.calculeazaTotal(), "card", true));
        comenzi.put(comanda.getId(), comanda);
        return comanda;
    }

    public Comanda cautaComandaDupaId(int id) throws ComandaInvalidaException {
        Comanda comanda = comenzi.get(id);
        if (comanda == null) {
            throw new ComandaInvalidaException("Comanda cu id " + id + " nu exista.");
        }
        return comanda;
    }

    public void asigneazaSofer(int comandaId, int soferId) throws ComandaInvalidaException, UserNotFoundException {
        Comanda comanda = cautaComandaDupaId(comandaId);
        Sofer sofer = UserService.getInstance().cautaSoferDupaId(soferId);
        comanda.setSofer(sofer);
        sofer.setDisponibil(false);
        comanda.setStatus(StatusComanda.ACCEPTATA);
    }

    public void actualizeazaStatus(int comandaId, StatusComanda status) throws ComandaInvalidaException {
        cautaComandaDupaId(comandaId).setStatus(status);
    }

    public void afiseazaComenziClient(int clientId) {
        for (Comanda comanda : comenzi.values()) {
            if (comanda.getClient().getId() == clientId) {
                System.out.println(comanda);
            }
        }
    }

    public void adaugaReview(Review review) throws RestaurantNotFoundException {
        cautaRestaurantDupaId(review.getRestaurant().getId()).adaugaReview(review);
    }

    public void afiseazaReviewuriRestaurant(int restaurantId) throws RestaurantNotFoundException {
        for (Review review : cautaRestaurantDupaId(restaurantId).getReviewuri()) {
            System.out.println(review);
        }
    }

    public double calculeazaVenitTotal() {
        double total = 0;
        for (Comanda comanda : comenzi.values()) {
            total += comanda.calculeazaTotal();
        }
        return total;
    }
}
