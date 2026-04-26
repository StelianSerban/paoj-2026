package com.pao.proiect.catalog.model;

public class Profesor extends Persoana {
    private String departament;
    private String titlu;

    public Profesor(String nume, String prenume, String email, String departament, String titlu) {
        super(nume, prenume, email);
        this.departament = departament;
        this.titlu = titlu;
    }

    public String getDepartament() { return departament; }
    public String getTitlu() { return titlu; }

    public void setDepartament(String departament) { this.departament = departament; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    @Override
    public String getRol() { return "Profesor"; }

    @Override
    public String toString() {
        return titlu + " " + super.toString() + " - Departament: " + departament;
    }
}