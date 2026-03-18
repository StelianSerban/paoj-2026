package com.pao.laboratory03.exercise.model;

public enum Subject {
    PAOJ("Programare Avansata pe Obiecte", 6),
    BD("Baze de date", 5),
    SO("Sisteme de operare", 5);




    private String fullName;
    private int credits;

    private Subject(String n, int c)
    {
        fullName = n;
        credits = c;
    }

    public String getFullName()
    {
        return fullName;
    }

    public int getCredits()
    {
        return credits;
    }

    public String toString()
    {
        return "PAOJ (Programare Avansata pe Obiecte, 6 credite)";
    }
}
