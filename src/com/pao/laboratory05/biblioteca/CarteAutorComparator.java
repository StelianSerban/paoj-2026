package com.pao.laboratory05.biblioteca;

import java.util.Comparator;

public class CarteAutorComparator implements Comparator<Carte> {
    @Override
    public int compare(Carte carte, Carte t1) {
        return carte.getAutor().compareTo(t1.getAutor());
    }
}
