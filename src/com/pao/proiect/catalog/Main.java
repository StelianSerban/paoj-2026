package com.pao.proiect.catalog;

import com.pao.proiect.catalog.model.*;
import com.pao.proiect.catalog.service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Catalog catalog = new Catalog("Catalog Facultate");

        Specializare specializare = new Specializare("Informatica", "Tehnic", 3);

        Profesor prof1 = new Profesor("Popescu", "Ion", "ion.popescu@uni.ro", "Informatica", "Prof. Dr.");
        Profesor prof2 = new Profesor("Ionescu", "Maria", "maria.ionescu@uni.ro", "Matematica", "Conf. Dr.");

        Materie materie1 = new Materie("Programare Avansata", 5, prof1);
        Materie materie2 = new Materie("Analiza Matematica", 4, prof2);

        Grupa grupa = new Grupa("A1", specializare);
        catalog.adaugaGrupa(grupa);

        Student s1 = new Student("Georgescu", "Andrei", "andrei.georgescu@stud.ro", grupa, 1);
        Student s2 = new Student("Muresan", "Elena", "elena.muresan@stud.ro", grupa, 1);
        Student s3 = new Student("Pop", "Mihai", "mihai.pop@stud.ro", grupa, 1);

        StudentService studentService = StudentService.getInstance(catalog);
        ProfesorService profesorService = ProfesorService.getInstance(catalog);

        System.out.println("\n--- 1. Adauga studenti ---");
        studentService.adaugaStudent(grupa, s1);
        studentService.adaugaStudent(grupa, s2);
        studentService.adaugaStudent(grupa, s3);

        System.out.println("\n--- 2. Adauga profesori ---");
        profesorService.adaugaProfesor(prof1);
        profesorService.adaugaProfesor(prof2);

        System.out.println("\n--- 3. Inregistreaza note ---");
        studentService.inregistreazaNota(s1, new Nota(9.5, LocalDate.now(), materie1));
        studentService.inregistreazaNota(s1, new Nota(8.0, LocalDate.now(), materie2));
        studentService.inregistreazaNota(s2, new Nota(7.5, LocalDate.now(), materie1));
        studentService.inregistreazaNota(s2, new Nota(9.0, LocalDate.now(), materie2));
        studentService.inregistreazaNota(s3, new Nota(6.0, LocalDate.now(), materie1));
        studentService.inregistreazaNota(s3, new Nota(8.5, LocalDate.now(), materie2));

        System.out.println("\n--- 4. Cauta student dupa nume ---");
        Student gasit = studentService.cautaDupaNume(grupa, "Georgescu");
        System.out.println("Student gasit: " + gasit);

        System.out.println("\n--- 5. Afiseaza notele lui Andrei ---");
        studentService.afiseazaNote(s1);

        System.out.println("\n--- 6. Calculeaza media ---");
        double medie = studentService.calculeazaMedie(s1, materie1);
        System.out.println("Media lui Andrei la " + materie1.getNume() + ": " + medie);

        System.out.println("\n--- 7. Listeaza studentii din grupa A1 ---");
        studentService.listeazaStudentiDinGrupa(grupa);

        System.out.println("\n--- 8. Afiseaza materiile lui prof1 ---");
        profesorService.afiseazaMaterii(prof1);

        System.out.println("\n--- 9. Studentul cu cea mai mare medie la Programare Avansata ---");
        Student topStudent = studentService.studentulCuCeaMaiMareMedie(grupa, materie1);
        System.out.println("Top student: " + topStudent);

        System.out.println("\n--- 10. Sterge student ---");
        studentService.stergeStudent(grupa, s3);
        studentService.listeazaStudentiDinGrupa(grupa);
    }
}