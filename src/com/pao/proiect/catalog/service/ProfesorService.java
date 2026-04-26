package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfesorService {
    private static ProfesorService instance;
    private Catalog catalog;

    private ProfesorService(Catalog catalog) {
        this.catalog = catalog;
    }

    public static ProfesorService getInstance(Catalog catalog) {
        if (instance == null) {
            instance = new ProfesorService(catalog);
        }
        return instance;
    }

    public void adaugaProfesor(Profesor profesor) {
        catalog.adaugaProfesor(profesor);
        System.out.println("Profesor adaugat: " + profesor);
    }

    public void afiseazaMaterii(Profesor profesor) {
        List<Materie> materii = new ArrayList<>();

        for (Grupa grupa : catalog.getGrupe()) {
            for (Student student : grupa.getStudenti()) {
                for (Nota nota : catalog.getNoteStudent(student)) {
                    Materie m = nota.getMaterie();
                    if (m.getProfesor().equals(profesor) && !materii.contains(m)) {
                        materii.add(m);
                    }
                }
            }
        }
        if (materii.isEmpty()) {
            System.out.println("Nicio materie gasita pentru " + profesor);
            return;
        }

        System.out.println("Materiile predate de " + profesor + ":");
        materii.forEach(System.out::println);
    }
}