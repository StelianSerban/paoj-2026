package com.pao.laboratory09.exercise3;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CoadaTranzactii coada = new CoadaTranzactii(5);
        AtomicInteger contor = new AtomicInteger(0);

        // 1. Creează 3 ATM-uri și un Processor
        ATMThread atm1 = new ATMThread(1, coada, contor);
        ATMThread atm2 = new ATMThread(2, coada, contor);
        ATMThread atm3 = new ATMThread(3, coada, contor);
        ProcessorThread processorThread = new ProcessorThread(coada);
        Thread processor = new Thread(processorThread);

        // 2 & 3. Pornește toți
        atm1.start();
        atm2.start();
        atm3.start();
        processor.start();

        // 4. Asteaptă terminarea ATM-urilor
        atm1.join();
        atm2.join();
        atm3.join();

        // 5. Oprește consumatorul
        processorThread.activ = false;
        coada.notificaToate();

        // 6. Asteaptă terminarea consumatorului
        processor.join();

        // 7. Mesaj final
        System.out.println("Toate tranzactiile procesate. Total: 12");
    }
}