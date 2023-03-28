package application.model;

import java.time.LocalDate;

public class Påfyldning {
    private int mængde;
    private Medarbejder medarbejder;
    private Destillat destillat;
    private LocalDate påfyldningsDato;

    public Påfyldning(int mængde, Fad fad, Medarbejder medarbejder, Destillat destillat, LocalDate påfyldningsDato) {
        this.mængde = mængde;
        this.medarbejder = medarbejder;
        this.destillat = destillat;
        this.påfyldningsDato = påfyldningsDato;
        fad.addPåfyldning(this);
    }

    public int getMængde() {
        return mængde;
    }

    public Medarbejder getMedarbejder() {
        return medarbejder;
    }

    public Destillat getDestillat() {
        return destillat;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }
}
