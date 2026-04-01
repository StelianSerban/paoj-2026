package com.pao.laboratory06.exercise1;

import java.util.Comparator;

public class AngajatSortNume implements Comparator<Angajat> {
    @Override
    public int compare(Angajat angajat, Angajat t1) {
        if(angajat.getNume().equals(t1.getNume()))
            return 0;
        if(angajat.getNume().compareTo(t1.getNume()) < 0)
            return -1;
        return 1;
    }
}
