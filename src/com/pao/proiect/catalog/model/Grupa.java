package com.pao.proiect.catalog.model;

import java.util.ArrayList;
import java.util.List;

public class Grupa {
    private String nume;
    private Specializare specializare;
    private List<Student> studenti;

    public Grupa(String nume, Specializare specializare) {
        this.nume = nume;
        this.specializare = specializare;
        this.studenti = new ArrayList<>();
    }

    public String getNume() { return nume; }
    public Specializare getSpecializare() { return specializare; }
    public List<Student> getStudenti() { return studenti; }

    public void setNume(String nume) { this.nume = nume; }
    public void setSpecializare(Specializare specializare) { this.specializare = specializare; }

    public void adaugaStudent(Student student) {
        studenti.add(student);
    }

    public void stergeStudent(Student student) {
        studenti.remove(student);
    }

    @Override
    public String toString() {
        return "Grupa " + nume + " - " + specializare.getNume() + " (" + studenti.size() + " studenti)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grupa)) return false;
        Grupa c = (Grupa) o;
        return nume.equals(c.nume);
    }

    @Override
    public int hashCode() {
        return nume.hashCode();
    }
}