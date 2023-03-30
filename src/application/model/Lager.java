package application.model;

import java.util.ArrayList;
import java.util.List;

public class Lager {
    private String navn;
    private String adresse;
    private final ArrayList<Hylde> hylder = new ArrayList<>();
    private final ArrayList<Integer> brugteHyldeNumre = new ArrayList<>();

    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
    }

    public ArrayList<Hylde> getHylder (){
        return new ArrayList<>(hylder);
    }

    public Hylde createHylde(int hyldeNr, Lager lager, int antalHyldePladser) {
        Hylde hylde = new Hylde(hyldeNr, lager, antalHyldePladser);
        hylder.add(hylde);
        brugteHyldeNumre.add(hyldeNr);
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

    public ArrayList<Integer> getBrugteHyldeNumre() {
        return brugteHyldeNumre;
    }

    @Override
    public String toString() {
        return navn;
    }

}
