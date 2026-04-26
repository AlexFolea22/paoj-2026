package com.pao.laboratory07.exercise3;

import com.pao.laboratory07.exercise1.OrderState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = scanner.next();
            Comanda comanda = switch (tip) {
                case "STANDARD" -> new ComandaStandard(scanner.next(), scanner.nextDouble(), scanner.next());
                case "DISCOUNTED" -> new ComandaRedusa(scanner.next(), scanner.nextDouble(), scanner.nextInt(), scanner.next());
                case "GIFT" -> new ComandaGratuita(scanner.next(), scanner.next());
                default -> throw new IllegalArgumentException("Tip invalid: " + tip);
            };
            comenzi.add(comanda);
        }

        for (Comanda comanda : comenzi) {
            System.out.println(comanda.descriereCompleta());
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();
            if (comanda.equals("QUIT")) {
                return;
            }

            switch (comanda) {
                case "STATS" -> afiseazaStats(comenzi);
                case "FILTER" -> afiseazaFiltrate(comenzi, scanner.nextDouble());
                case "SORT" -> afiseazaSortate(comenzi);
                case "SPECIAL" -> afiseazaSpeciale(comenzi);
                default -> throw new IllegalArgumentException("Comanda invalida: " + comanda);
            }
        }
    }

    private static void afiseazaStats(List<Comanda> comenzi) {
        System.out.println("\n--- STATS ---");
        Map<String, Double> medii = comenzi.stream()
                .collect(Collectors.groupingBy(Comanda::tip, Collectors.averagingDouble(Comanda::pretFinal)));
        for (String tip : List.of("STANDARD", "DISCOUNTED", "GIFT")) {
            if (medii.containsKey(tip)) {
                System.out.printf("%s: medie = %.2f lei%n", tip, medii.get(tip));
            }
        }
    }

    private static void afiseazaFiltrate(List<Comanda> comenzi, double prag) {
        System.out.printf("%n--- FILTER (>= %.2f) ---%n", prag);
        comenzi.stream()
                .filter(c -> c.pretFinal() >= prag)
                .forEach(c -> System.out.println(c.descriereFaraStare()));
    }

    private static void afiseazaSortate(List<Comanda> comenzi) {
        System.out.println("\n--- SORT (by client, then by pret) ---");
        comenzi.stream()
                .sorted(Comparator.comparing(Comanda::getClient).thenComparingDouble(Comanda::pretFinal))
                .forEach(c -> System.out.println(c.descriereFaraStare()));
    }

    private static void afiseazaSpeciale(List<Comanda> comenzi) {
        System.out.println("\n--- SPECIAL (discount > 15%) ---");
        comenzi.stream()
                .filter(c -> c instanceof ComandaRedusa redusa && redusa.getDiscountProcent() > 15)
                .forEach(c -> System.out.println(c.descriereSpeciala()));
    }
}

abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected double pret;
    protected String client;
    protected OrderState stare = OrderState.PLACED;

    public Comanda(String nume, double pret, String client) {
        this.nume = nume;
        this.pret = pret;
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public abstract String tip();

    public abstract double pretFinal();

    public abstract String descriereCompleta();

    public abstract String descriereFaraStare();

    public String descriereSpeciala() {
        return descriereFaraStare();
    }
}

final class ComandaStandard extends Comanda {
    public ComandaStandard(String nume, double pret, String client) {
        super(nume, pret, client);
    }

    @Override
    public String tip() {
        return "STANDARD";
    }

    @Override
    public double pretFinal() {
        return pret;
    }

    @Override
    public String descriereCompleta() {
        return String.format("STANDARD: %s, pret: %.2f lei [%s] - client: %s", nume, pretFinal(), stare, client);
    }

    @Override
    public String descriereFaraStare() {
        return String.format("STANDARD: %s, pret: %.2f lei - client: %s", nume, pretFinal(), client);
    }
}

final class ComandaRedusa extends Comanda {
    private int discountProcent;

    public ComandaRedusa(String nume, double pret, int discountProcent, String client) {
        super(nume, pret, client);
        this.discountProcent = discountProcent;
    }

    public int getDiscountProcent() {
        return discountProcent;
    }

    @Override
    public String tip() {
        return "DISCOUNTED";
    }

    @Override
    public double pretFinal() {
        return pret * (1 - discountProcent / 100.0);
    }

    @Override
    public String descriereCompleta() {
        return String.format("DISCOUNTED: %s, pret: %.2f lei (-%d%%) [%s] - client: %s",
                nume, pretFinal(), discountProcent, stare, client);
    }

    @Override
    public String descriereFaraStare() {
        return String.format("DISCOUNTED: %s, pret: %.2f lei - client: %s", nume, pretFinal(), client);
    }

    @Override
    public String descriereSpeciala() {
        return String.format("DISCOUNTED: %s, pret: %.2f lei (-%d%%) - client: %s",
                nume, pretFinal(), discountProcent, client);
    }
}

final class ComandaGratuita extends Comanda {
    public ComandaGratuita(String nume, String client) {
        super(nume, 0, client);
    }

    @Override
    public String tip() {
        return "GIFT";
    }

    @Override
    public double pretFinal() {
        return 0;
    }

    @Override
    public String descriereCompleta() {
        return String.format("GIFT: %s, gratuit [%s] - client: %s", nume, stare, client);
    }

    @Override
    public String descriereFaraStare() {
        return String.format("GIFT: %s, gratuit - client: %s", nume, client);
    }
}
