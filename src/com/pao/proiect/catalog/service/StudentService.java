package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.exception.NotaInvalidaException;
import com.pao.proiect.catalog.exception.StudentNotFoundException;
import com.pao.proiect.catalog.model.*;
import com.pao.proiect.catalog.repository.NotaRepository;
import com.pao.proiect.catalog.repository.StudentRepository;

import java.util.*;

public class StudentService {
    private static StudentService instance;
    private Catalog catalog;
    private final StudentRepository studentRepository = new StudentRepository();
    private final NotaRepository notaRepository = new NotaRepository();
    private final AuditService audit = AuditService.getInstance();

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
        studentRepository.save(student);
        audit.log("adauga_student");
        System.out.println("Student adaugat: " + student);
    }

    public void stergeStudent(Grupa grupa, Student student) {
        if (!grupa.getStudenti().contains(student)) {
            throw new StudentNotFoundException(student.getNume(), student.getPrenume());
        }
        grupa.stergeStudent(student);
        studentRepository.deleteByEmail(student.getEmail());
        audit.log("sterge_student");
        System.out.println("Student sters: " + student);
    }

    public Student cautaDupaNume(Grupa grupa, String nume) {
        audit.log("cauta_student_dupa_nume");

        for (Student s : grupa.getStudenti()) {
            if (s.getNume().equalsIgnoreCase(nume)) {
                return s;
            }
        }
        List<Student> rezultate = studentRepository.findByNume(nume);
        if (!rezultate.isEmpty()) {
            return rezultate.get(0);
        }
        throw new StudentNotFoundException(nume, "");
    }

    public void afiseazaNote(Student student) {
        audit.log("afiseaza_note_student");
        List<Nota> note = notaRepository.findByStudent(student);
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
        audit.log("calculeaza_medie_student");
        List<Nota> note = notaRepository.findByStudent(student);
        double suma = 0;
        int count = 0;
        for (Nota n : note) {
            if (n.getMaterie().equals(materie)) {
                suma += n.getValoare();
                count++;
            }
        }
        return count == 0 ? 0.0 : suma / count;
    }

    public void listeazaStudentiDinGrupa(Grupa grupa) {
        audit.log("listeaza_studenti_din_grupa");
        System.out.println("Studenti din grupa " + grupa.getNume() + ":");
        List<Student> studenti = studentRepository.findByGrupa(grupa.getNume());
        if (studenti.isEmpty()) {
            studenti = new ArrayList<>(grupa.getStudenti());
        }
        Collections.sort(studenti);
        for (Student s : studenti) {
            System.out.println(s);
        }
    }

    public Student studentulCuCeaMaiMareMedie(Grupa grupa, Materie materie) {
        audit.log("student_cu_cea_mai_mare_medie");

        return notaRepository.findTopStudentLaMaterie(materie.getNume())
                .orElseThrow(() -> new StudentNotFoundException("Niciun student in grupa."));
    }

    public void inregistreazaNota(Student student, Nota nota) {
        if (nota.getValoare() < 1 || nota.getValoare() > 10) {
            throw new NotaInvalidaException(nota.getValoare());
        }
        catalog.adaugaNota(student, nota);
        notaRepository.saveForStudent(student, nota);
        audit.log("inregistreaza_nota");
        System.out.println("Nota inregistrata: " + nota + " pentru " + student);
    }
}