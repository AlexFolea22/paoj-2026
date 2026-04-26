package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        List<Student> studenti = com.pao.laboratory08.exercise1.Main.citesteStudenti();
        Scanner scanner = new Scanner(System.in);
        int prag = scanner.nextInt();

        List<Student> filtrati = new ArrayList<>();
        for (Student student : studenti) {
            if (student.getVarsta() >= prag) {
                filtrati.add(student);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rezultate.txt"))) {
            for (Student student : filtrati) {
                writer.write(student.toString());
                writer.newLine();
            }
        }

        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");
        System.out.println();
        for (Student student : filtrati) {
            System.out.println(student);
        }
        System.out.println();
        System.out.println("Scris in: rezultate.txt");
    }
}
