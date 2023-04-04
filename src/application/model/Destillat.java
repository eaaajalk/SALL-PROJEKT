package application.model;

import application.controller.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

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

    /**
     * Opretter nyt destillat objekt. <br />
     * Requires:
     * @param startDato != null
     * @param slutDato != null, slutdato > startdato
     * @param mængde != null
     * @param medarbejder != null
     * @param kommentar
     * @param vandType != null
     * @param maltBatch != null
     * @param ID != null, must be unique
     * @param alkoholProcent != null
     */
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

        if (slutDato.isBefore(startDato)) {
            throw new IllegalArgumentException("Slut dato er mindre end start dato");
        } if (mængde <= 0) {
            throw new IllegalArgumentException("Mængde må ikke være 0 eller mindre");
        } if (Integer.parseInt(ID) <= 0) {
            throw new IllegalArgumentException("ID må ikke være negativ");
        } if (alkoholProcent < 0 || alkoholProcent > 100) {
            throw new IllegalArgumentException("Alkoholprocenten skal være mellem 0 til 100");
        }
    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    /**
     * Opretter et ny Påfyldning objekt. <br />
     * Requires:
     * @param mængde != null, mængde > 0, mængde < fad størrelse
     * @param fad != null
     * @param medarbejder != null
     * @param destillat != null
     * @param påfyldningsDato != null, påfyldningsdato >= destillat slutdato
     * @return
     */
    public Påfyldning createPåfyldning(int mængde, Fad fad, Medarbejder medarbejder, Destillat destillat, LocalDate påfyldningsDato) {
        if (mængde <= 0) {
            throw new IllegalArgumentException("Mængden må ikke være 0 eller negativt");
        } if (mængde > fad.getStr()) {
            throw new IllegalArgumentException("Mængden må ikke være større end fad størrelsen");
        } if (påfyldningsDato.isBefore(destillat.getSlutDato())) {
            throw new IllegalArgumentException("Påfyldningsdatoen må ikke fremkomme før destillat slutdatoen");
        } else {
        Påfyldning påfyldning = new Påfyldning(mængde, fad, medarbejder, destillat, påfyldningsDato);
        påfyldninger.add(påfyldning);
        return påfyldning;
        }
    }

    /**
     * Tilføjere Destillat obkejtet et Påfyldning objekt. <br />
     * Requires:
     * @param påfyldning != null
     */
    public void addPåfyldning(Påfyldning påfyldning) {
        if (påfyldning == null) {
            throw new IllegalArgumentException("Påfyldning må ikke være null");
        } else {
            if (!påfyldninger.contains(påfyldning)) {
                påfyldninger.add(påfyldning);
        }
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

    public LocalDate getStartDato() {
        return startDato;
    }

    public int getMængde() {
        return mængde;
    }

    public String getKommentar() {
        return kommentar;
    }

    public String getVandType() {
        return vandType;
    }
}
