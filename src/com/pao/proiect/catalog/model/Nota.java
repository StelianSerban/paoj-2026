package com.pao.proiect.catalog.model;

import java.time.LocalDate;

public final class Nota {
    private final double valoare;
    private final LocalDate data;
    private final Materie materie;

    public Nota(double valoare, LocalDate data, Materie materie) {
        this.valoare = valoare;
        this.data = data;
        this.materie = materie;
    }

    public double getValoare() { return valoare; }
    public LocalDate getData() { return data; }
    public Materie getMaterie() { return new Materie(materie.getNume(), materie.getCredite(), materie.getProfesor()); }

    @Override
    public String toString() {
        return materie.getNume() + ": " + valoare + " (" + data + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nota)) return false;
        Nota n = (Nota) o;
        return Double.compare(valoare, n.valoare) == 0
                && data.equals(n.data)
                && materie.equals(n.materie);
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(valoare) + data.hashCode() + materie.hashCode();
    }
}