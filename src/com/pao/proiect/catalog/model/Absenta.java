package com.pao.proiect.catalog.model;

import java.time.LocalDate;

public class Absenta {
    private Student student;
    private Materie materie;
    private LocalDate data;
    private boolean motivata;

    public Absenta(Student student, Materie materie, LocalDate data, boolean motivata) {
        this.student = student;
        this.materie = materie;
        this.data = data;
        this.motivata = motivata;
    }

    public Student getStudent() { return student; }
    public Materie getMaterie() { return materie; }
    public LocalDate getData() { return data; }
    public boolean isMotivata() { return motivata; }

    public void setMotivata(boolean motivata) { this.motivata = motivata; }

    @Override
    public String toString() {
        return student.getPrenume() + " " + student.getNume()
                + " - " + materie.getNume()
                + " - " + data
                + (motivata ? " (motivata)" : " (nemotivata)");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Absenta)) return false;
        Absenta a = (Absenta) o;
        return student.equals(a.student)
                && materie.equals(a.materie)
                && data.equals(a.data);
    }

    @Override
    public int hashCode() {
        return student.hashCode() + materie.hashCode() + data.hashCode();
    }
}