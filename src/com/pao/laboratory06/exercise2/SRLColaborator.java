package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica implements IOperatiiCitireScriere{
    private double cheltuieli_lunare;

    @Override
    public double calculeazaVenitNetAnual() {
        return (venit_brut - cheltuieli_lunare) * 12 * 0.84;
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
