package com.pao.proiect.catalog.model;

public class Specializare {
    private String nume;
    private String domeniu;
    private int durata;

    public Specializare(String nume, String domeniu, int durata) {
        this.nume = nume;
        this.domeniu = domeniu;
        this.durata = durata;
    }

    public String getNume() { return nume; }
    public String getDomeniu() { return domeniu; }
    public int getDurata() { return durata; }

    public void setNume(String nume) { this.nume = nume; }
    public void setDomeniu(String domeniu) { this.domeniu = domeniu; }
    public void setDurata(int durata) { this.durata = durata; }

    @Override
    public String toString() {
        return nume + " (" + domeniu + ", " + durata + " ani)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specializare)) return false;
        Specializare s = (Specializare) o;
        return nume.equals(s.nume);
    }

    @Override
    public int hashCode() {
        return nume.hashCode();
    }
}