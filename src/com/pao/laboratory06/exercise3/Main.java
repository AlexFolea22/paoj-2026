package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Inginer[] ingineri = {
                new Inginer("Popescu", "Ana", "0712345678", 7000),
                new Inginer("Ionescu", "Mihai", "0722222222", 8500),
                new Inginer("Dumitru", "Elena", "", 6500)
        };

        System.out.println("--- Ingineri sortati dupa nume ---");
        Arrays.sort(ingineri);
        for (Inginer inginer : ingineri) {
            System.out.println(inginer);
        }

        System.out.println("\n--- Ingineri sortati dupa salariu ---");
        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer inginer : ingineri) {
            System.out.println(inginer);
        }

        System.out.println("\n--- Referinta PlataOnline ---");
        PlataOnline plata = ingineri[0];
        plata.autentificare("ana", "parola");
        System.out.println("Sold: " + plata.consultareSold());
        System.out.println("Plata 1200: " + plata.efectuarePlata(1200));

        System.out.println("\n--- Referinta PlataOnlineSMS ---");
        PersoanaJuridica firma = new PersoanaJuridica("Tech", "SRL", "0733333333", 50000);
        PlataOnlineSMS plataSMS = firma;
        System.out.println("SMS trimis: " + plataSMS.trimiteSMS("Confirmare plata"));
        System.out.println("Mesaje: " + firma.getSmsTrimise());

        PersoanaJuridica faraTelefon = new PersoanaJuridica("NoPhone", "SRL", "", 20000);
        System.out.println("SMS fara telefon: " + faraTelefon.trimiteSMS("Test"));
        System.out.println("SMS mesaj gol: " + firma.trimiteSMS(""));

        System.out.println("\n--- Constanta financiara ---");
        System.out.println("TVA = " + ConstanteFinanciare.TVA.getValoare());

        System.out.println("\n--- Erori tratate ---");
        try {
            plata.autentificare(null, "x");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            ingineri[0].trimiteSMS("Nu se poate");
        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }
}

interface PlataOnline {
    void autentificare(String user, String parola);

    double consultareSold();

    boolean efectuarePlata(double suma);
}

interface PlataOnlineSMS extends PlataOnline {
    boolean trimiteSMS(String mesaj);
}

enum ConstanteFinanciare {
    TVA(0.19),
    SALARIU_MINIM(4050),
    COTA_IMPOZIT(0.10);

    private final double valoare;

    ConstanteFinanciare(double valoare) {
        this.valoare = valoare;
    }

    public double getValoare() {
        return valoare;
    }
}

abstract class Persoana {
    protected String nume;
    protected String prenume;
    protected String telefon;

    public Persoana(String nume, String prenume, String telefon) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
    }
}

class Angajat extends Persoana {
    protected double salariu;

    public Angajat(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon);
        this.salariu = salariu;
    }
}

class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private double sold;
    private boolean autentificat;

    public Inginer(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon, salariu);
        this.sold = salariu * 2;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User/parola invalide.");
        }
        autentificat = true;
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (!autentificat || suma <= 0 || suma > sold) {
            return false;
        }
        sold -= suma;
        return true;
    }

    public boolean trimiteSMS(String mesaj) {
        throw new UnsupportedOperationException("Inginerul nu are capabilitate SMS.");
    }

    @Override
    public int compareTo(Inginer other) {
        return nume.compareTo(other.nume);
    }

    @Override
    public String toString() {
        return nume + " " + prenume + " - salariu: " + salariu;
    }
}

class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private double sold;
    private boolean autentificat;
    private List<String> smsTrimise = new ArrayList<>();

    public PersoanaJuridica(String nume, String prenume, String telefon, double sold) {
        super(nume, prenume, telefon);
        this.sold = sold;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User/parola invalide.");
        }
        autentificat = true;
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (!autentificat || suma <= 0 || suma > sold) {
            return false;
        }
        sold -= suma;
        return true;
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (telefon == null || telefon.isBlank() || mesaj == null || mesaj.isBlank()) {
            return false;
        }
        smsTrimise.add(mesaj);
        return true;
    }

    public List<String> getSmsTrimise() {
        return smsTrimise;
    }
}

class ComparatorInginerSalariu implements Comparator<Inginer> {
    @Override
    public int compare(Inginer a, Inginer b) {
        return Double.compare(b.salariu, a.salariu);
    }
}
