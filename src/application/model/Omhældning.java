package application.model;

import java.time.LocalDate;

public class Omhældning {
    private Fad fraFad, tilFad;
    private int mængde;
    private LocalDate omhældningsDato;

    Omhældning(Fad fraFad, int mængde, LocalDate omhældningsDato, Fad tilFad) {
        this.fraFad = fraFad;
        this.mængde = mængde;
        this.omhældningsDato = omhældningsDato;
        this.tilFad = tilFad;
    }
    public Fad getFraFad() {
        return fraFad;
    }
    public int getMængde() {
        return mængde;
    }
    public void setMængde(int mængde) {
        this.mængde = mængde;
    }
    public LocalDate getOmhældningsDato() {
        return omhældningsDato;
    }
    /**
     * Opdaterer mængden af indhold i fra og til -fad. Hvis fra-fadet bliver tømt, nulstilles den og 'Whisky' føjes til historikken. <br />
     */
    public void updateFade(){
        fraFad.setIndholdsMængde(fraFad.getIndholdsMængde()-mængde);
        if (fraFad.getIndholdsMængde() == 0) {
            fraFad.addFadHistorik("Whisky Destillat");
            fraFad.nulstilFad();
        }
        tilFad.addIndholdsMængde(mængde);
    }
}
