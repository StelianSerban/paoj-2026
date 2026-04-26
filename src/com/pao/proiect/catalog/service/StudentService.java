package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.exception.NotaInvalidaException;
import com.pao.proiect.catalog.exception.StudentNotFoundException;
import com.pao.proiect.catalog.model.*;

import java.util.*;

public class StudentService {
    private static StudentService instance;
    private Catalog catalog;

    private StudentService(Catalog catalog) {
        this.catalog = catalog;
    }

    public static StudentService getInstance(Catalog catalog) {
        if (instance == null) {
            instance = new StudentService(catalog);
        }
        return instance;
    }

    public void adaugaStudent(Grupa grupa, Student student) {
        grupa.adaugaStudent(student);
        System.out.println("Student adaugat: " + student);
    }

    public void stergeStudent(Grupa grupa, Student student) {
        if (!grupa.getStudenti().contains(student)) {
            throw new StudentNotFoundException(student.getNume(), student.getPrenume());
        }
        grupa.stergeStudent(student);
        System.out.println("Student sters: " + student);
    }

    public Student cautaDupaNume(Grupa grupa, String nume) {
        for (Student s : grupa.getStudenti()) {
            if (s.getNume().equalsIgnoreCase(nume)) {
                return s;
            }
        }
        throw new StudentNotFoundException(nume, "");
    }

    public void afiseazaNote(Student student) {
        List<Nota> note = catalog.getNoteStudent(student);
        if (note.isEmpty()) {
            System.out.println("Nicio nota pentru " + student);
            return;
        }
        System.out.println("Notele studentului " + student + ":");
        for (Nota n : note) {
            System.out.println(n);
        }
    }

    public double calculeazaMedie(Student student, Materie materie) {
        List<Nota> note = catalog.getNoteStudent(student);
        double suma = 0;
        int count = 0;
        for (Nota n : note) {
            if (n.getMaterie().equals(materie)) {
                suma += n.getValoare();
                count++;
            }
        }
        if(count == 0) {
            return 0.0;
        }
        else {
            return suma / count;
        }
    }

    public void listeazaStudentiDinGrupa(Grupa grupa) {
        System.out.println("Studenti din grupa " + grupa.getNume() + ":");
        List<Student> studenti = new ArrayList<>(grupa.getStudenti());
        Collections.sort(studenti);
        for (Student s : studenti) {
            System.out.println(s);
        }
    }

    public Student studentulCuCeaMaiMareMedie(Grupa grupa, Materie materie) {
        Student topStudent = null;
        double topMedie = -1;
        for (Student s : grupa.getStudenti()) {
            double medie = calculeazaMedie(s, materie);
            if (medie > topMedie) {
                topMedie = medie;
                topStudent = s;
            }
        }
        if (topStudent == null) {
            throw new StudentNotFoundException("Niciun student in grupa.");
        }
        return topStudent;
    }

    public void inregistreazaNota(Student student, Nota nota) {
        if (nota.getValoare() < 1 || nota.getValoare() > 10) {
            throw new NotaInvalidaException(nota.getValoare());
        }
        catalog.adaugaNota(student, nota);
        System.out.println("Nota inregistrata: " + nota + " pentru " + student);
    }
}