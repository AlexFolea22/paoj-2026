package com.pao.laboratory08.exercise1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = citesteStudenti();
        Scanner scanner = new Scanner(System.in);
        String linie = scanner.nextLine();
        String[] parts = linie.split(" ", 2);

        if (parts[0].equals("PRINT")) {
            for (Student student : studenti) {
                System.out.println(student);
            }
            return;
        }

        Student original = cautaStudent(studenti, parts[1]);
        Student clona = parts[0].equals("SHALLOW") ? original.shallowClone() : original.deepClone();
        clona.getAdresa().setOras("MODIFICAT");

        System.out.println("Original: " + original);
        System.out.println("Clona: " + clona);
    }

    public static List<Student> citesteStudenti() throws IOException {
        List<Student> studenti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                if (linie.isBlank()) {
                    continue;
                }
                String[] parts = linie.split(",");
                String nume = parts[0].trim();
                int varsta = Integer.parseInt(parts[1].trim());
                Adresa adresa = new Adresa(parts[2].trim(), parts[3].trim());
                studenti.add(new Student(nume, varsta, adresa));
            }
        }
        return studenti;
    }

    private static Student cautaStudent(List<Student> studenti, String nume) {
        for (Student student : studenti) {
            if (student.getNume().equals(nume)) {
                return student;
            }
        }
        throw new IllegalArgumentException("Student inexistent: " + nume);
    }
}
