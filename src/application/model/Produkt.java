package application.model;

import java.time.LocalDate;

public class Produkt {
    private int nr;
    private WhiskyBatch whiskyBatch;
    private int pris;
    private LocalDate tapningsDato;
    private int unikID;
    private static int idCount = 0;


    public Produkt(int nr, WhiskyBatch whiskyBatch, int pris, LocalDate tapningsDato) {
        this.nr = nr;
        this.whiskyBatch = whiskyBatch;
        this.pris = pris;
        this.tapningsDato = tapningsDato;
        this.unikID = idCount;
        idCount++;
    }

    public String toString () {
        return  "ID: " + unikID + " | Nr " + nr + "/ " + whiskyBatch.getProdukter().size() + " | Batch: " + whiskyBatch.getBatchID();
    }
}
