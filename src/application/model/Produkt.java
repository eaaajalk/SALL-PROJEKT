package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Produkt {
    private int nr;
    private WhiskyBatch whiskyBatch;
    private int pris;
    private LocalDate tapningsDato;
    private int unikID;
    private double flaskeStr; //flaske søtrrelse i L
    private static int idCount = 1;
    public Produkt(int nr, WhiskyBatch whiskyBatch, int pris, LocalDate tapningsDato, double flaskeStr) {
        this.nr = nr;
        this.whiskyBatch = whiskyBatch;
        this.pris = pris;
        this.tapningsDato = tapningsDato;
        this.unikID = idCount;
        this.flaskeStr = flaskeStr;
        idCount++;

    }

    public int getNr() {
        return nr;
    }

    public WhiskyBatch getWhiskyBatch() {
        return whiskyBatch;
    }

    public int getPris() {
        return pris;
    }

    public LocalDate getTapningsDato() {
        return tapningsDato;
    }

    public int getUnikID() {
        return unikID;
    }

    public static int getIdCount() {
        return idCount;
    }

    public String produktHistorie() {
        StringBuilder sb = new StringBuilder();

        String id = "ID: " + getUnikID();
        String nr = "Nr: " + getNr() + "/" + whiskyBatch.getProdukter().size();
        String dato = "Tapningsdato: " + getTapningsDato();
        String alkohol = "Alkoholprocent: " + whiskyBatch.getAlkoholProcent();
        String type = "Type: " + whiskyBatch.getType();

        sb.append(id).append("\n").append(nr).append("\n").append(dato).append("\n").append(alkohol).append("\n").append(type);

        return String.valueOf(sb);
    }


@Override
    public String toString () {
        return  "ID: " + unikID + " | Nr " + nr + "/ " + whiskyBatch.getProdukter().size() + " | Batch: " + whiskyBatch.getBatchID();
    }
}
