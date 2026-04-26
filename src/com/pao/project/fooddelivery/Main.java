package com.pao.project.fooddelivery;

import com.pao.project.fooddelivery.exception.ComandaInvalidaException;
import com.pao.project.fooddelivery.exception.RestaurantNotFoundException;
import com.pao.project.fooddelivery.exception.UserNotFoundException;
import com.pao.project.fooddelivery.model.Adresa;
import com.pao.project.fooddelivery.model.Client;
import com.pao.project.fooddelivery.model.Comanda;
import com.pao.project.fooddelivery.model.Produs;
import com.pao.project.fooddelivery.model.Restaurant;
import com.pao.project.fooddelivery.model.Review;
import com.pao.project.fooddelivery.model.Sofer;
import com.pao.project.fooddelivery.model.StatusComanda;
import com.pao.project.fooddelivery.service.FoodDeliveryService;
import com.pao.project.fooddelivery.service.UserService;

public class Main {
    public static void main(String[] args)
            throws RestaurantNotFoundException, UserNotFoundException, ComandaInvalidaException {
        UserService userService = UserService.getInstance();
        FoodDeliveryService foodService = FoodDeliveryService.getInstance();

        Client ana = new Client(1, "Popescu", "Ana", "ana@mail.com", "0711111111",
                new Adresa("Bucuresti", "Calea Victoriei", 10));
        Client mihai = new Client(2, "Ionescu", "Mihai", "mihai@mail.com", "0722222222",
                new Adresa("Cluj", "Strada Memorandumului", 5));
        Sofer sofer1 = new Sofer(10, "Dima", "Vlad", "0733333333", "B-100-PAO", 4.9);
        Sofer sofer2 = new Sofer(11, "Marin", "Elena", "0744444444", "CJ-200-PAO", 4.6);

        userService.adaugaClient(ana);
        userService.adaugaClient(mihai);
        userService.adaugaSofer(sofer1);
        userService.adaugaSofer(sofer2);

        Restaurant sushi = new Restaurant(100, "Sushi House",
                new Adresa("Bucuresti", "Strada Japoniei", 7), 4.8);
        Restaurant pizza = new Restaurant(101, "Pizza Buna",
                new Adresa("Cluj", "Strada Napoca", 3), 4.5);

        foodService.adaugaRestaurant(sushi);
        foodService.adaugaRestaurant(pizza);
        foodService.adaugaProdusInMeniu(100, new Produs(1, "Salmon Roll", "Sushi", 45));
        foodService.adaugaProdusInMeniu(100, new Produs(2, "Miso Soup", "Supa", 18));
        foodService.adaugaProdusInMeniu(101, new Produs(3, "Pizza Margherita", "Pizza", 38));
        foodService.adaugaProdusInMeniu(101, new Produs(4, "Tiramisu", "Desert", 22));

        System.out.println("--- Restaurante ---");
        foodService.listeazaRestaurante();
        System.out.println("\n--- Restaurante Bucuresti ---");
        foodService.cautaRestauranteDupaOras("Bucuresti").forEach(System.out::println);
        System.out.println("\n--- Meniu Sushi House ---");
        foodService.listeazaMeniuRestaurant(100);

        Comanda comanda1 = foodService.plaseazaComanda(1, 100, new int[]{1, 2}, new int[]{2, 1});
        foodService.asigneazaSofer(comanda1.getId(), 10);
        foodService.actualizeazaStatus(comanda1.getId(), StatusComanda.IN_LIVRARE);
        foodService.actualizeazaStatus(comanda1.getId(), StatusComanda.LIVRATA);

        Comanda comanda2 = foodService.plaseazaComanda(2, 101, new int[]{3, 4}, new int[]{1, 2});
        foodService.asigneazaSofer(comanda2.getId(), 11);

        foodService.adaugaReview(new Review(1, ana, sushi, 5, "Foarte bun."));
        foodService.adaugaReview(new Review(2, mihai, pizza, 4, "Livrare rapida."));

        System.out.println("\n--- Comenzi Ana ---");
        foodService.afiseazaComenziClient(1);
        System.out.println("\n--- Soferi sortati dupa rating ---");
        userService.listeazaSoferiSortati();
        System.out.println("\n--- Reviews Sushi House ---");
        foodService.afiseazaReviewuriRestaurant(100);
        System.out.println("\nVenit total platforma: " + foodService.calculeazaVenitTotal());
    }
}
