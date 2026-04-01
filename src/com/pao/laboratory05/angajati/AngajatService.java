package com.pao.laboratory05.angajati;

import com.pao.laboratory05.biblioteca.Carte;

import java.util.Arrays;

public class AngajatService
{
    private static AngajatService instance;
    private Angajat[] angajati = new Angajat[0];

    private AngajatService(){}
    public static AngajatService getInstance() {
        if (instance == null) {
            instance = new AngajatService();
        }
        return instance;
    }

    void addAngajat(Angajat a)
    {
        Angajat[] nou = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, nou, 0, angajati.length);
        nou[angajati.length] = a;
        angajati = nou;
        System.out.println("Angajat adaugat: " + a.getNume());
    }

    void printAll()
    {
        for(Angajat a : angajati) {
            System.out.println(a.toString());
        }
    }

    void listBySalary()
    {
        Angajat[] copy = new Angajat[angajati.length];
        System.arraycopy(angajati, 0, copy, 0, angajati.length);
        Arrays.sort(copy);
    }

    void findByDepartment(String numeDept)
    {
        boolean gasit = false;
        for(Angajat a : angajati)
        {
            if(a.getDepartament().nume().equalsIgnoreCase(numeDept))
            {
                System.out.println(a.toString());
                gasit = true;
            }
        }
        if(gasit == false)
        {
            System.out.println("Niciun angajat in departamentul: " + numeDept);
        }
    }


}
