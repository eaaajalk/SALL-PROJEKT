package application.model;

import java.util.ArrayList;

public class Lager {
    private String navn;
    private String adresse;
    private final ArrayList<Hylde> hylder = new ArrayList<>();

    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
    }

    public ArrayList<Hylde> getHylder (){
        return new ArrayList<>(hylder);
    }

    public Hylde createHylde(int hyldeNr, Lager lager) {
        Hylde hylde = new Hylde(hyldeNr, lager);
        hylder.add(hylde);
        return hylde;
    }

    public void addHylde(Hylde hylde) {
        if (!hylder.contains(hylde)) {
        hylder.add(hylde);
    }
    }

    public void removeHylde(Hylde hylde) {
        if (hylder.contains(hylde)) {
        hylder.remove(hylde);
    }
    }

}
