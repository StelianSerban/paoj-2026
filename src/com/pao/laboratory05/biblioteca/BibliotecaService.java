package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService
{
    private Carte[] carti = new Carte[0];
    private BibliotecaService(){}
    private static BibliotecaService instance;
    public static BibliotecaService getInstance()
    {
        if(instance == null)
            instance = new BibliotecaService();
        return instance;
    }

    void addCarte(Carte carte)
    {
        Carte[] nou = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, nou, 0, carti.length);
        nou[carti.length] = carte;
        carti = nou;
        System.out.println("Carte adaugata: " + carte.getTitlu());
    }

    void listSortedByRating()
    {
        Carte[] copy = new Carte[carti.length];
        System.arraycopy(carti, 0, copy, 0, carti.length);
        Arrays.sort(copy);
        for(int i = 0; i < copy.length; i++)
        {
            System.out.println((i + 1) + ". " + copy[i].toString());
        }
    }

    void listSortedBy(Comparator<Carte> comparator)
    {
        Carte[] copy = new Carte[carti.length];
        System.arraycopy(carti, 0, copy, 0, carti.length);
        Arrays.sort(copy, comparator);
        for(int i = 0; i < copy.length; i++)
        {
            System.out.println((i + 1) + ". " + copy[i].toString());
        }
    }

}
