package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti = new Carte[0];

    private BibliotecaService() {
    }

    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }

    public void addCarte(Carte carte) {
        Carte[] copie = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, copie, 0, carti.length);
        copie[carti.length] = carte;
        carti = copie;

        System.out.println("Carte adaugata: " + carte);
    }

    public void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy);

        System.out.println("Carti sortate dupa rating:");
        for (Carte carte : copy) {
            System.out.println(carte);
        }
    }

    public void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);

        System.out.println("Carti sortate:");
        for (Carte carte : copy) {
            System.out.println(carte);
        }
    }
}