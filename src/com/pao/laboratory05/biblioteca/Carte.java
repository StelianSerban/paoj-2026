package com.pao.laboratory05.biblioteca;

public class Carte implements Comparable<Carte>{

    private String titlu;
    private String autor;
    private int an;
    private double rating;

    public Carte(String t, String a, int an, double r)
    {
        titlu = t;
        autor = a;
        this.an = an;
        rating = r;
    }

    public int getAn() {
        return an;
    }

    public double getRating() {
        return rating;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitlu() {
        return titlu;
    }

    public String toString()
    {
        return "Carte{titlu='" + titlu + "', autor='" + autor
                + "', an='" + an + "', rating=" + rating + "}";

    }

    @Override
    public int compareTo(Carte carte) {
        if(carte.rating < this.rating)
        {
            return -1;
        }
        if(carte.rating > this.rating)
        {
            return 1;
        }
        return 0;
    }
}
