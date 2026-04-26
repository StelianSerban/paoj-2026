package com.pao.proiect.catalog.model;

public abstract class Persoana {
    private String nume;
    private String prenume;
    private String email;

    public Persoana(String nume, String prenume, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public String getEmail() { return email; }

    public void setNume(String nume) { this.nume = nume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }
    public void setEmail(String email) { this.email = email; }

    public abstract String getRol();

    @Override
    public String toString() {
        return prenume + " " + nume + " (" + email + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persoana)) return false;
        Persoana p = (Persoana) o;
        return email.equals(p.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}