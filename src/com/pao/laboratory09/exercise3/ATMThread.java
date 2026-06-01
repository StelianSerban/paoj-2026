package com.pao.laboratory09.exercise3;

import java.util.concurrent.atomic.AtomicInteger;

public class ATMThread extends Thread {
    private final int atmId;
    private final CoadaTranzactii coada;
    private final AtomicInteger contor;

    private static final double[] SUME = {250.00, 500.00, 1200.00, 750.50};
    private static final String DATA = "2024-01-15";

    public ATMThread(int atmId, CoadaTranzactii coada, AtomicInteger contor) {
        this.atmId = atmId;
        this.coada = coada;
        this.contor = contor;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            int id = contor.incrementAndGet();
            double suma = SUME[i];
            Tranzactie t = new Tranzactie(id, suma, DATA, atmId);
            try {
                coada.adauga(t);
                System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON%n", atmId, id, suma);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}