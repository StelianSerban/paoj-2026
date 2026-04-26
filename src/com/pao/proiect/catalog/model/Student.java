package com.pao.proiect.catalog.model;

public class Student extends Persoana implements Comparable<Student>{
    private Grupa grupa;
    private int an;

    public Student(String nume, String prenume, String email, Grupa grupa, int an) {
        super(nume, prenume, email);
        this.grupa = grupa;
        this.an = an;
    }

    public Grupa getGrupa() { return grupa; }
    public int getAn() { return an; }

    public void setGrupa(Grupa grupa) { this.grupa = grupa; }
    public void setAn(int an) { this.an = an; }

    @Override
    public String getRol() { return "Student"; }

    @Override
    public String toString() {
        return super.toString() + " - Grupa: " + grupa.getNume() + ", An: " + an;
    }

    @Override
    public int compareTo(Student student) {
        return this.getNume().compareTo(student.getNume());
    }
}