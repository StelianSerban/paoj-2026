package com.pao.laboratory06.exercise1;

import java.util.Comparator;

public class AngajatSortSalariu implements Comparator<Angajat>
{

    @Override
    public int compare(Angajat angajat, Angajat t1) {
        if(angajat.getSalariu() < t1.getSalariu())
        {
            return -1;
        }
        if(angajat.getSalariu() == t1.getSalariu())
            return 0;
        return 1;
    }
}
