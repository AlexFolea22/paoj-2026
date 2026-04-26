# Proiect Etapa I - Platforma Food Delivery

## Actiuni / interogari disponibile

1. Inregistreaza un client nou.
2. Inregistreaza un sofer nou.
3. Adauga un restaurant nou.
4. Adauga produse in meniul unui restaurant.
5. Listeaza toate restaurantele.
6. Cauta restaurante dupa oras.
7. Listeaza meniul unui restaurant.
8. Plaseaza o comanda pentru un client.
9. Asigneaza un sofer unei comenzi.
10. Actualizeaza statusul unei comenzi.
11. Afiseaza comenzile unui client.
12. Afiseaza soferii sortati dupa rating.
13. Adauga un review pentru un restaurant.
14. Calculeaza venitul total al platformei.

## Tipuri de obiecte

1. `Persoana`
2. `Client`
3. `Sofer`
4. `Adresa`
5. `Restaurant`
6. `Meniu`
7. `Produs`
8. `Comanda`
9. `LinieComanda`
10. `Plata`
11. `Review`
12. `StatusComanda`

## Colectii folosite

- `List<Restaurant>` pentru restaurante.
- `Map<Integer, Comanda>` pentru comenzile indexate dupa id.
- `TreeSet<Sofer>` pentru soferi sortati dupa rating si nume.
- `Set<Client>` pentru clientii unici.

## Ierarhie

`Persoana` este clasa abstracta de baza, extinsa de `Client` si `Sofer`.

## Servicii

- `UserService` gestioneaza clientii si soferii.
- `FoodDeliveryService` gestioneaza restaurantele, meniurile, comenzile, review-urile si statisticile.
