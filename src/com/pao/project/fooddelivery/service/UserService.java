package com.pao.project.fooddelivery.service;

import com.pao.project.fooddelivery.exception.UserNotFoundException;
import com.pao.project.fooddelivery.model.Client;
import com.pao.project.fooddelivery.model.Sofer;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class UserService {
    private static UserService instance;

    private Set<Client> clienti = new HashSet<>();
    private TreeSet<Sofer> soferi = new TreeSet<>();

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void adaugaClient(Client client) {
        clienti.add(client);
    }

    public void stergeClient(int id) throws UserNotFoundException {
        clienti.remove(cautaClientDupaId(id));
    }

    public Client cautaClientDupaId(int id) throws UserNotFoundException {
        for (Client client : clienti) {
            if (client.getId() == id) {
                return client;
            }
        }
        throw new UserNotFoundException("Clientul cu id " + id + " nu exista.");
    }

    public void listeazaClienti() {
        for (Client client : clienti) {
            System.out.println(client);
        }
    }

    public void adaugaSofer(Sofer sofer) {
        soferi.add(sofer);
    }

    public void stergeSofer(int id) throws UserNotFoundException {
        soferi.remove(cautaSoferDupaId(id));
    }

    public Sofer cautaSoferDupaId(int id) throws UserNotFoundException {
        for (Sofer sofer : soferi) {
            if (sofer.getId() == id) {
                return sofer;
            }
        }
        throw new UserNotFoundException("Soferul cu id " + id + " nu exista.");
    }

    public void listeazaSoferiSortati() {
        for (Sofer sofer : soferi) {
            System.out.println(sofer);
        }
    }
}
