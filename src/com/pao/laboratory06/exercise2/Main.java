package com.pao.laboratory06.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Colaborator> colaboratori = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = in.next();
            Colaborator colaborator = switch (tip) {
                case "CIM" -> new CIMColaborator(in);
                case "PFA" -> new PFAColaborator(in);
                case "SRL" -> new SRLColaborator(in);
                default -> throw new IllegalArgumentException("Tip necunoscut: " + tip);
            };
            colaboratori.add(colaborator);
        }

        for (TipColaborator tip : TipColaborator.values()) {
            colaboratori.stream()
                    .filter(c -> c.getTip() == tip)
                    .sorted((a, b) -> Double.compare(b.calculeazaVenitNetAnual(), a.calculeazaVenitNetAnual()))
                    .forEach(Colaborator::afiseaza);
        }

        Colaborator max = colaboratori.stream()
                .max(Comparator.comparingDouble(Colaborator::calculeazaVenitNetAnual))
                .orElse(null);

        System.out.println();
        System.out.print("Colaborator cu venit net maxim: ");
        if (max != null) {
            max.afiseaza();
        }

        System.out.println();
        System.out.println("Colaboratori persoane juridice:");
        colaboratori.stream()
                .filter(c -> c instanceof PersoanaJuridica)
                .sorted((a, b) -> Double.compare(b.calculeazaVenitNetAnual(), a.calculeazaVenitNetAnual()))
                .forEach(Colaborator::afiseaza);

        Map<TipColaborator, Double> sume = new EnumMap<>(TipColaborator.class);
        Map<TipColaborator, Integer> numere = new EnumMap<>(TipColaborator.class);
        for (Colaborator colaborator : colaboratori) {
            TipColaborator tip = colaborator.getTip();
            sume.put(tip, sume.getOrDefault(tip, 0.0) + colaborator.calculeazaVenitNetAnual());
            numere.put(tip, numere.getOrDefault(tip, 0) + 1);
        }

        System.out.println();
        System.out.println("Sume și număr colaboratori pe tip:");
        for (TipColaborator tip : TipColaborator.values()) {
            Double suma = sume.get(tip);
            Integer numar = numere.get(tip);
            if (suma == null) {
                System.out.printf("%s: suma = nu lei, număr = null%n", tip);
            } else {
                System.out.printf("%s: suma = %.2f lei, număr = %d%n", tip, suma, numar);
            }
        }
    }
}

enum TipColaborator {
    CIM, PFA, SRL
}

interface IOperatiiCitireScriere {
    void afiseaza();

    String tipContract();

    default boolean areBonus() {
        return false;
    }
}

interface PersoanaFizica {
}

interface PersoanaJuridica {
}

abstract class Colaborator implements IOperatiiCitireScriere {
    protected String nume;
    protected String prenume;
    protected double venitBrutLunar;

    public Colaborator(String nume, String prenume, double venitBrutLunar) {
        this.nume = nume;
        this.prenume = prenume;
        this.venitBrutLunar = venitBrutLunar;
    }

    public abstract double calculeazaVenitNetAnual();

    public abstract TipColaborator getTip();

    @Override
    public String tipContract() {
        return getTip().name();
    }

    @Override
    public void afiseaza() {
        System.out.printf("%s: %s %s, venit net anual: %.2f lei%n",
                tipContract(), nume, prenume, calculeazaVenitNetAnual());
    }
}

class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus;

    public CIMColaborator(Scanner in) {
        super(in.next(), in.next(), in.nextDouble());
        if (in.hasNext("DA|NU")) {
            bonus = in.next().equals("DA");
        }
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venit = venitBrutLunar * 12 * 0.55;
        return bonus ? venit * 1.10 : venit;
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }
}

class PFAColaborator extends Colaborator implements PersoanaFizica {
    private static final double SALARIU_MINIM_LUNAR = 4050;
    private double cheltuieliLunare;

    public PFAColaborator(Scanner in) {
        super(in.next(), in.next(), in.nextDouble());
        cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;
        double salariuMinimAnual = SALARIU_MINIM_LUNAR * 12;
        double impozit = venitNet * 0.10;
        double cass;
        if (venitNet < 6 * salariuMinimAnual) {
            cass = 6 * salariuMinimAnual * 0.10;
        } else if (venitNet <= 72 * salariuMinimAnual) {
            cass = venitNet * 0.10;
        } else {
            cass = 72 * salariuMinimAnual * 0.10;
        }

        double cas = 0;
        if (venitNet >= 12 * salariuMinimAnual && venitNet <= 24 * salariuMinimAnual) {
            cas = 12 * salariuMinimAnual * 0.25;
        } else if (venitNet > 24 * salariuMinimAnual) {
            cas = 24 * salariuMinimAnual * 0.25;
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }
}

class SRLColaborator extends Colaborator implements PersoanaJuridica {
    private double cheltuieliLunare;

    public SRLColaborator(Scanner in) {
        super(in.next(), in.next(), in.nextDouble());
        cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieliLunare) * 12 * 0.84;
    }

    @Override
    public TipColaborator getTip() {
        return TipColaborator.SRL;
    }
}
