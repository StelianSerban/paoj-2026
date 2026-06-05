package com.pao.proiect.catalog.service;

import com.pao.proiect.catalog.model.*;
import com.pao.proiect.catalog.repository.MaterieRepository;
import com.pao.proiect.catalog.repository.ProfesorRepository;

import java.util.List;

public class ProfesorService {
    private static ProfesorService instance;
    private Catalog catalog;
    private final ProfesorRepository profesorRepository = new ProfesorRepository();
    private final MaterieRepository materieRepository = new MaterieRepository();
    private final AuditService audit = AuditService.getInstance();

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
        profesorRepository.save(profesor);
        audit.log("adauga_profesor");
        System.out.println("Profesor adaugat: " + profesor);
    }

    public void afiseazaMaterii(Profesor profesor) {
        audit.log("afiseaza_materii_profesor");
        List<Materie> materii = materieRepository.findByProfesor(profesor.getEmail());
        if (materii.isEmpty()) {
            System.out.println("Nicio materie gasita pentru " + profesor);
            return;
        }
        System.out.println("Materiile predate de " + profesor + ":");
        materii.forEach(System.out::println);
    }
}