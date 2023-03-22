package application.model;

import java.util.ArrayList;

public class Hylde {
    private int hyldeNr;

    private Lager lager;
    private final ArrayList<Fad> fade = new ArrayList<>();

    public Hylde (int hyldeNr, Lager lager) {
        this.hyldeNr = hyldeNr;
        this.lager = lager;
    }

    public int getHyldeNr() {
        return hyldeNr;
    }

    public Lager getLager() {
        return lager;
    }
    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
            fad.setHylde(this);
        }
    }
    public void removeFad(Fad fad) {
        if (fade.contains(fad)) {
            fade.remove(fad);
            fad.setHylde(null);
        }
    }




}
