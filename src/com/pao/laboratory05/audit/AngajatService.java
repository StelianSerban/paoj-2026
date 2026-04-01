package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;

import java.util.Arrays;

public class AngajatService
{
    private static AngajatService instance;
    private com.pao.laboratory05.angajati.Angajat[] angajati = new com.pao.laboratory05.angajati.Angajat[0];

    private AngajatService(){}
    public static AngajatService getInstance() {
        if (instance == null) {
            instance = new AngajatService();
        }
        return instance;
    }

    void addAngajat(com.pao.laboratory05.angajati.Angajat a)
    {
        com.pao.laboratory05.angajati.Angajat[] nou = new com.pao.laboratory05.angajati.Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, nou, 0, angajati.length);
        nou[angajati.length] = a;
        angajati = nou;
        System.out.println("Angajat adaugat: " + a.getNume());
    }

    void printAll()
    {
        for(com.pao.laboratory05.angajati.Angajat a : angajati) {
            System.out.println(a.toString());
        }
    }

    void listBySalary()
    {
        com.pao.laboratory05.angajati.Angajat[] copy = new com.pao.laboratory05.angajati.Angajat[angajati.length];
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
