package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Fad {
    private String ID;
    private ArrayList<String> fadHistorik = new ArrayList<>();
    private String str;
    private String kommentar;
    private Hylde hylde;
    private LocalDate lagerDato;

    public Fad(String ID, String fadHistorik, String str, String kommentar) {
        this.ID = ID;
        this.fadHistorik.add(fadHistorik);
        this.str = str;
        this.kommentar = kommentar;
    }

    public void setHylde(Hylde hylde, int Plads) {
        if (this.hylde != hylde) {
            Hylde oldHylde = this.hylde;
            if (oldHylde != null) {
                oldHylde.removeFad(this);
            }
            this.hylde = hylde;
            if (hylde != null)
                hylde.addFad(this, Plads);
        }
    }

    public Hylde getHylde(){
        return hylde;
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
        return "Hylde: " + getHylde().getHyldeNr() + " Plads: " + getHylde().getHyldePlads(this);
    }

    @Override
    public String toString() {
        return ID + "                                " + getHylde().getHyldeNr() + "                                       " + getHylde().getHyldePlads(this);
    }
}
