package application.model;

import java.time.LocalDate;

public class Fad {
    private String ID;
    private String fadType;
    private String str;
    private String kommentar;
    private Hylde hylde;
    private String plads;
    private LocalDate lagerDato;


    public Fad(String ID, String fadType, String str, String kommentar, LocalDate lagerDato) {
        this.ID = ID;
        this.fadType = fadType;
        this.str = str;
        this.kommentar = kommentar;
        this.lagerDato = lagerDato;
    }

    public void setHylde(Hylde hylde) {
        if (this.hylde != hylde) {
            Hylde oldHylde = this.hylde;
            if (oldHylde != null) {
                oldHylde.removeFad(this);
            }
            this.hylde = hylde;
            if (hylde != null)
                hylde.addFad(this);
        }
    }
    public Hylde getHylde(){
        return hylde;
    }

    public String getPlads() {
        return plads;
    }

    public void setPlads(String plads) {
        this.plads = plads;
    }

    public void setLagerDato(LocalDate lagerDato) {
        this.lagerDato = lagerDato;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public String getFadPlacering () {
        return "Hylde: " + getHylde().getHyldeNr() + " Plads: " + getPlads();
    }

    @Override
    public String toString() {
        return ID + "                                " + getHylde().getHyldeNr() + "                                       " + getPlads();
    }
}
