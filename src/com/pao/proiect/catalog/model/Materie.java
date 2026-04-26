package com.pao.proiect.catalog.model;

public class Materie {
    private String nume;
    private int credite;
    private Profesor profesor;

    public Materie(String nume, int credite, Profesor profesor) {
        this.nume = nume;
        this.credite = credite;
        this.profesor = profesor;
    }

    public String getNume() { return nume; }
    public int getCredite() { return credite; }
    public Profesor getProfesor() { return profesor; }

    public void setNume(String nume) { this.nume = nume; }
    public void setCredite(int credite) { this.credite = credite; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    @Override
    public String toString() {
        return nume + " (" + credite + " credite) - " + profesor.getTitlu() + " " + profesor.getNume();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Materie)) return false;
        Materie m = (Materie) o;
        return nume.equals(m.nume);
    }

    @Override
    public int hashCode() {
        return nume.hashCode();
    }
}