package com.pao.proiect.catalog.exception;

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException(String mesaj) {
        super(mesaj);
    }

    public NotaInvalidaException(double valoare) {
        super("Nota " + valoare + " este invalida. Valoarea trebuie sa fie intre 1 si 10.");
    }
}