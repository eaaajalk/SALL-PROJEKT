package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Destillat {
    private LocalDate startDato;
    private LocalDate slutDato;
    private int mængde;
    private Medarbejder medarbejder;
    private int alkoholProcent = 0;
    private String kommentar;
    private String vandType;
    private MaltBatch maltBatch;
    private String ID;
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>();

    public Destillat(LocalDate startDato, LocalDate slutDato, int mængde, Medarbejder medarbejder, String kommentar, String vandType, MaltBatch maltBatch, String ID, int alkoholProcent) {
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.mængde = mængde;
        this.medarbejder = medarbejder;
        this.kommentar = kommentar;
        this.vandType = vandType;
        this.maltBatch = maltBatch;
        this.ID = ID;
        this.alkoholProcent = alkoholProcent;

    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }
    public Påfyldning createPåflydning(int mængde, Fad fad, Medarbejder medarbejder, Destillat destillat, LocalDate påfyldningsDato) {
        Påfyldning påfyldning = new Påfyldning(mængde, fad, medarbejder, destillat, påfyldningsDato);
        påfyldninger.add(påfyldning);
        return påfyldning;
    }
    public void addPåfyldning(Påfyldning påfyldning) {
        if (!påfyldninger.contains(påfyldning)) {
        påfyldninger.add(påfyldning);
    }
    }
    public void removePåfyldning(Påfyldning påfyldning) {
        if (påfyldninger.contains(påfyldning)) {
        påfyldninger.remove(påfyldning);
    }
    }
    public Medarbejder getMedarbejder() {
        return medarbejder;
    }
    public MaltBatch getMaltBatch() {
        return maltBatch;
    }

    public String getID() {
        return ID;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public int getAlkoholProcent() {
        return alkoholProcent;
    }
}
