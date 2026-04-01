package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Departament;

public class Angajat implements Comparable<Angajat>{

    private String nume;
    private Departament departament;
    private double salariu;

    public Angajat(String n, Departament d, double s)
    {
        nume = n;
        departament = d;
        salariu = s;
    }

    public Departament getDepartament() {
        return departament;
    }

    public double getSalariu() {
        return salariu;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public String toString()
    {
        return "Angajat{nume='" + nume + "', departament=" + departament.toString()
                + ", salariu=" + salariu + "}";
    }

    @Override
    public int compareTo(Angajat angajat) {
        if(salariu == angajat.salariu)
        {
            return 0;
        }
        if(salariu > angajat.salariu)
        {
            return -1;
        }
        return 1;
    }
}
