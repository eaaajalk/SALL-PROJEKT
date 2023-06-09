package application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hylde {
    private int hyldeNr; // Hyldens nummer.
    private Lager lager; // Link til lager.
    private Map<Integer, Fad> fade = new HashMap<>();
    private int antalHyldePladser; // Det antal pladser der er på hylden.

    Hylde (int hyldeNr, Lager lager, int antalHyldePladser) {
        this.hyldeNr = hyldeNr;
        this.lager = lager;
        this.antalHyldePladser = antalHyldePladser;
        opretPladser(antalHyldePladser);
    }
    /**
     * Opretter automatisk et HashMap der holder styr på hvor mange pladser der er på en hylde. <br />
     */
    public int getHyldeNr() {
        return hyldeNr;
    }

    public Lager getLager() {
        return lager;
    }
    /**
     * @return Hyldens Fad-objekter som liste af typen fad.
     */
    public ArrayList<Fad> getFadeList() {
        return new ArrayList<>(fade.values());
    }
    /**
     * @return Hyldens map der indeholder Fade som values og deres pladser som keys.
     */
    public Map<Integer, Fad> getFadeMap() {
        return new HashMap<>(fade);
    }
    /**
     * Tilføjer et fad til en plads på hylden. <br />
     */
    public void opretPladser(int antalPladser){
        for (int i = 1; i <= antalPladser; i++) {
            fade.put(i, null);
        }
    }
    public void addFad(Fad fad, int Plads) {
        if (fade.size() >= Plads && Plads > 0) {
            if (!fade.containsValue(fad)) {
                if (fade.get(Plads) == null) {
                    fade.put(Plads, fad);
                    fad.setHylde(this, Plads);
                }
            }
        }
    }
    public void removeFad(Fad fad) {
        if (fade.containsValue(fad)) {
            int key = getHyldePlads(fad);
            fade.put(key, null);
            fad.setHylde(null, getHyldePlads(fad));
        }
    }
    /**
     * Returnerer et Fad's hyldeplads. <br />
     * Requires:
     * @param fad skal stå på en hylde.
     * @return fadets plads
     */
    public int getHyldePlads (Fad fad) {
        int key = -1;
        for (Map.Entry<Integer, Fad> entry : fade.entrySet()) {
            if (entry.getValue() == fad) {
                key = entry.getKey();
            }
        }
        return key;
    }
        public int getAntalPladser() {
        return antalHyldePladser;
    }
    @Override
    public String toString(){
        return String.valueOf(hyldeNr);
    }
}