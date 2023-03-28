package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Fad {
    private String ID;
    private ArrayList<String> fadHistorik = new ArrayList<>();
    private int str;
    private String kommentar;
    private Hylde hylde;
    private LocalDate lagerDato;
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>();

    public Fad(String ID, String fadHistorik, int str, String kommentar) {
        this.ID = ID;
        this.fadHistorik.add(fadHistorik);
        this.str = str;
        this.kommentar = kommentar;
    }

    public String getID() {
        return ID;
    }

    public int getStr() {
        return str;
    }

    public LocalDate getLagerDato() {
        return lagerDato;
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

    public ArrayList<String> getFadHistorik(){
        return new ArrayList<>(fadHistorik);
    }

    public int getFadPlads () {
        return getHylde().getHyldePlads(this);
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

    @Override
    public String toString() {
        return ID + "                                " + getHylde().getHyldeNr() + "                                       " + getHylde().getHyldePlads(this)+ "                       " + getHylde().getLager().toString();
    }
}
