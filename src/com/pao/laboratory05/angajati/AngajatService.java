package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati = new Angajat[0];

    private AngajatService() {
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a) {
        Angajat[] copie = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, copie, 0, angajati.length);
        copie[angajati.length] = a;
        angajati = copie;

        System.out.println("Angajat adaugat: " + a);
    }

    public void printAll() {
        System.out.println("Lista angajatilor:");
        for (Angajat angajat : angajati) {
            System.out.println(angajat);
        }
    }

    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);

        System.out.println("Angajati sortati dupa salariu:");
        for (Angajat angajat : copy) {
            System.out.println(angajat);
        }
    }

    public void findByDepartament(String numeDept) {
        boolean gasit = false;

        for (Angajat angajat : angajati) {
            if (angajat.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(angajat);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }
}