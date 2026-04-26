package com.pao.proiect.catalog.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String mesaj) {
        super(mesaj);
    }

    public StudentNotFoundException(String nume, String prenume) {
        super("Studentul " + prenume + " " + nume + " nu a fost gasit in catalog.");
    }
}