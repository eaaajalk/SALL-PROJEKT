package application.model;

import java.time.LocalDate;

public class Produkt {
    private int nr;
    private WhiskyBatch whiskyBatch;
    private int pris;
    private LocalDate tapningsDato;


    public Produkt(int nr, WhiskyBatch whiskyBatch, int pris, LocalDate tapningsDato) {
        this.nr = nr;
        this.whiskyBatch = whiskyBatch;
        this.pris = pris;
        this.tapningsDato = tapningsDato;
    }
}
