package application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hylde {
    private int hyldeNr;
    private Lager lager;
    private Map<Integer, Fad> fade = new HashMap<>();
    private int antalHyldePladser;


    public Hylde (int hyldeNr, Lager lager, int antalHyldePladser) {
        this.hyldeNr = hyldeNr;
        this.lager = lager;
        this.antalHyldePladser = antalHyldePladser;
        opretPladser(antalHyldePladser);
    }

    public void opretPladser(int antalPladser){
        for (int i = 1; i <= antalPladser; i++) {
            fade.put(i, null);
        }
    }

    public int getHyldeNr() {
        return hyldeNr;
    }

    public Lager getLager() {
        return lager;
    }
    public ArrayList<Fad> getFadeList() {
        return new ArrayList<>(fade.values());
    }
    public Map<Integer, Fad> getFadeMap() {
        return new HashMap<>(fade);
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

    public int getHyldePlads (Fad fad) {
        int key = -1;
        for (Map.Entry<Integer, Fad> entry : fade.entrySet()) {
            if (entry.getValue() == fad) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }

        public int getAntalPladser() {
        return antalHyldePladser;
    }
}