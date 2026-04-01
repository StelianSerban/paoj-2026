package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica implements IOperatiiCitireScriere{

    private double cheltuieli_lunare;
    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net = (venit_brut - cheltuieli_lunare) * 12;
        double impozit_pe_venit = venit_net * 0.1;
        double CASS;
        if(venit_net < 6 * 48600)
        {
            CASS = 6 * 48600 * 0.1;
        }
        else if(venit_net >= 6 * 48600 && venit_net <= 72 * 48600)
        {
            CASS = venit_net * 0.1;
        }
        else
        {
            CASS = 72 * 48600 * 0.1;
        }
        double CAS;
        if(venit_net < 12 * 48600)
        {
            CAS = 0;
        }
        else if(venit_net >= 12 * 48600 && venit_net <= 24 * 48600)
        {
            CAS = 12 * 48600 * 0.25;
        }
        else
        {
            CAS = 24 * 48600 * 0.25;
        }

        return venit_net - impozit_pe_venit - CASS - CAS;
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
