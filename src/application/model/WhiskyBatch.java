package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WhiskyBatch {
    private String batchID;
    private int fortyndningsMængde;
    private int alkoholProcent;
    private Map<Fad, Integer> fade = new HashMap<>();
    private int modningsTid;
    private String beskrivelse;
    private LocalDate produktionsDato;
    private ArrayList<Produkt> flasker = new ArrayList<Produkt>();
    private int mængde;

    public WhiskyBatch(String batchID, int fortyndningsMængde, int alkoholProcent, int modningsTid, String beskrivelse, LocalDate produktionsDato, int fadMængde, Fad fad) {
        this.batchID = batchID;
        this.fortyndningsMængde = fortyndningsMængde;
        this.alkoholProcent = alkoholProcent;
        this.modningsTid = modningsTid;
        this.beskrivelse = beskrivelse;
        this.produktionsDato = produktionsDato;

    }

    public Produkt createFlaske(int nr, int pris, LocalDate tapningsDato) {
        Produkt produkt = new Produkt(nr, this, pris, tapningsDato);
        flasker.add(produkt);
        return produkt;
    }

    public void tapPåFlasker(int pris, LocalDate tapningsDato, int antalFlasker) {
        for (int i = 1; i < antalFlasker; i++) {
            createFlaske(i, pris, tapningsDato);
        }
    }

    public String getBatchID() {
        return batchID;
    }

    public int getFortyndningsMængde() {
        return fortyndningsMængde;
    }

    public int getAlkoholProcent() {
        return alkoholProcent;
    }

    public HashMap<Fad, Integer> getFade() {
        return new HashMap<>(fade);
    }

    public int getModningsTid() {
        return modningsTid;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public LocalDate getProduktionsDato() {
        return produktionsDato;
    }

    public ArrayList<Produkt> getFlasker() {
        return flasker;
    }
    public void addFad(Fad fad, int mængde) {
        if (fad.getIndholdsMængde() >= mængde) {
            if (!fade.containsKey(fad)) {
                fade.put(fad, mængde);
                fad.setIndholdsMængde(fad.getIndholdsMængde()-mængde);
                } else {
                int m = fade.get(fad);
                fade.put(fad, m + mængde);
                fad.setIndholdsMængde(fad.getIndholdsMængde()-mængde);
            }
        } else {
            throw new RuntimeException("Mængden er større end fadets indhold");
        }
    }


    @Override
    public String toString() {
        return "Batch ID: " + batchID;
    }


}

