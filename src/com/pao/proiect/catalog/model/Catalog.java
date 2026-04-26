package com.pao.proiect.catalog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog {
    private String nume;
    private List<Grupa> grupe;
    private Map<Student, List<Nota>> note;
    private Map<Student, List<Absenta>> absente;
    private List<Profesor> profesori = new ArrayList<>();

    public Catalog(String nume) {
        this.nume = nume;
        this.grupe = new ArrayList<>();
        this.note = new HashMap<>();
        this.absente = new HashMap<>();
        this.profesori = new ArrayList<>();
    }

    public String getNume() { return nume; }
    public List<Grupa> getGrupe() { return grupe; }
    public Map<Student, List<Nota>> getNote() { return note; }
    public Map<Student, List<Absenta>> getAbsente() { return absente; }
    public List<Profesor> getProfesori() {return profesori; }

    public void adaugaProfesor(Profesor profesor){
        profesori.add(profesor);
    }

    public void adaugaGrupa(Grupa grupa) {
        grupe.add(grupa);
    }

    public void adaugaNota(Student student, Nota nota) {
        if (!note.containsKey(student)) {
            note.put(student, new ArrayList<>());
        }
        note.get(student).add(nota);
    }

    public void adaugaAbsenta(Student student, Absenta absenta) {
        absente.computeIfAbsent(student, k -> new ArrayList<>()).add(absenta);
    }

    public List<Nota> getNoteStudent(Student student) {
        return note.getOrDefault(student, new ArrayList<>());
    }

    public List<Absenta> getAbsenteStudent(Student student) {
        return absente.getOrDefault(student, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "Catalog: " + nume + " (" + grupe.size() + " grupe)";
    }
}