package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (scanner.hasNextLine()) {
            String linie = scanner.nextLine().trim();
            if (linie.isEmpty()) continue;
            String[] parts = linie.split(" ", 2);
            String comanda = parts[0];

            switch (comanda) {
                case "ENQUEUE" -> {
                    coada.addLast(parseTranzactie(parts[1]));
                }
                case "DEQUEUE" -> {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Procesat: " + coada.removeFirst());
                    }
                }
                case "PUSH" -> {
                    coada.addFirst(parseTranzactie(parts[1]));
                }
                case "POP" -> {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Extras: " + coada.removeFirst());
                    }
                }
                case "REMOVE_DEBIT" -> {
                    int count = 0;
                    Iterator<Tranzactie> itr = coada.iterator();
                    while (itr.hasNext()) {
                        if (itr.next().getTip() == TipTranzactie.DEBIT) {
                            itr.remove();
                            count++;
                        }
                    }
                    System.out.println("Eliminat " + count + " tranzactii DEBIT.");
                }
                case "REMOVE_BELOW" -> {
                    double threshold = Double.parseDouble(parts[1]);
                    int count = 0;
                    Iterator<Tranzactie> itr = coada.iterator();
                    while (itr.hasNext()) {
                        if (itr.next().getSuma() < threshold) {
                            itr.remove();
                            count++;
                        }
                    }
                    System.out.printf("Eliminat %d tranzactii sub %.2f RON.%n", count, threshold);
                }
                case "PRINT" -> {
                    for (Tranzactie t : coada) {
                        System.out.println(t);
                    }
                }
                case "SIZE" -> {
                    System.out.println("Dimensiune coada: " + coada.size());
                }
            }
        }
    }

    private static Tranzactie parseTranzactie(String s) {
        String[] p = s.split(" ");
        int id = Integer.parseInt(p[0]);
        double suma = Double.parseDouble(p[1]);
        String data = p[2];
        TipTranzactie tip = TipTranzactie.valueOf(p[3]);
        return new Tranzactie(id, suma, data, tip);
    }
}