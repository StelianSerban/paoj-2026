package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica implements IOperatiiCitireScriere{
    @Override
    public double calculeazaVenitNetAnual() {
        double rezultat = venit_brut * 12 * 0.55;
        if(areBonus())
            rezultat += rezultat * 0.1;
        return rezultat;
    }

    @Override
    public void citeste(Scanner in) {

    }

    @Override
    public void afiseaza() {

    }

    @Override
    public String tipContract() {
        return "";
    }
}
