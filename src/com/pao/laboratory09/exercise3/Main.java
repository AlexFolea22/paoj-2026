package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Vezi Readme.md pentru cerințe

        CoadaTranzactii coada = new CoadaTranzactii();
        ProcessorThread processor = new ProcessorThread(coada);
        Thread processorThread = new Thread(processor);

        ATMThread atm1 = new ATMThread(1, coada);
        ATMThread atm2 = new ATMThread(2, coada);
        ATMThread atm3 = new ATMThread(3, coada);

        processorThread.start();
        atm1.start();
        atm2.start();
        atm3.start();

        atm1.join();
        atm2.join();
        atm3.join();

        processor.activ = false;
        coada.opreste();
        processorThread.join();

        System.out.println("Toate tranzactiile procesate. Total: " + processor.total);
    }

    static class Tranzactie {
        int id;
        double suma;
        String data;

        Tranzactie(int id, double suma, String data) {
            this.id = id;
            this.suma = suma;
            this.data = data;
        }
    }

    static class CoadaTranzactii {
        private final Queue<Tranzactie> coada = new LinkedList<>();
        private boolean inchisa = false;

        public synchronized void adauga(Tranzactie tranzactie, int atmId) throws InterruptedException {
            while (coada.size() == 5) {
                System.out.println("[ATM-" + atmId + "] astept loc...");
                wait();
            }
            coada.add(tranzactie);
            notifyAll();
        }

        public synchronized Tranzactie extrage() throws InterruptedException {
            while (coada.isEmpty() && !inchisa) {
                wait();
            }
            if (coada.isEmpty()) {
                return null;
            }
            Tranzactie tranzactie = coada.poll();
            notifyAll();
            return tranzactie;
        }

        public synchronized void opreste() {
            inchisa = true;
            notifyAll();
        }

        public synchronized boolean esteGoala() {
            return coada.isEmpty();
        }
    }

    static class ATMThread extends Thread {
        private static int urmatorulId = 1;

        private final int atmId;
        private final CoadaTranzactii coada;

        ATMThread(int atmId, CoadaTranzactii coada) {
            this.atmId = atmId;
            this.coada = coada;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 4; i++) {
                    int id = genereazaId();
                    Tranzactie tranzactie = new Tranzactie(id, 100 + id * 10, "2024-05-01");
                    System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON%n",
                            atmId, tranzactie.id, tranzactie.suma);
                    coada.adauga(tranzactie, atmId);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                System.out.println("[ATM-" + atmId + "] oprit");
            }
        }

        private static synchronized int genereazaId() {
            return urmatorulId++;
        }
    }

    static class ProcessorThread implements Runnable {
        private final CoadaTranzactii coada;
        volatile boolean activ = true;
        int total = 0;

        ProcessorThread(CoadaTranzactii coada) {
            this.coada = coada;
        }

        @Override
        public void run() {
            try {
                while (activ || !coada.esteGoala()) {
                    Tranzactie tranzactie = coada.extrage();
                    if (tranzactie == null) {
                        break;
                    }
                    Thread.sleep(80);
                    total++;
                    System.out.printf("[Processor] Factura #%d - %.2f RON | %s%n",
                            tranzactie.id, tranzactie.suma, tranzactie.data);
                }
            } catch (InterruptedException e) {
                System.out.println("[Processor] oprit");
            }
        }
    }
}
