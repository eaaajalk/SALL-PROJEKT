package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private LocalDate batchDato;
    private ArrayList<Produkt> produkter = new ArrayList<Produkt>();
    private int batchMængde;
    private LocalDate lagringsDato;

    public WhiskyBatch(String batchID, int fortyndningsMængde, int alkoholProcent, String beskrivelse, LocalDate batchDato, int fadMængde, Fad fad) {
        this.batchID = batchID;
        this.fortyndningsMængde = fortyndningsMængde;
        this.alkoholProcent = alkoholProcent;
        this.beskrivelse = beskrivelse;
        this.batchDato = batchDato;
        this.batchMængde = fortyndningsMængde;
        this.lagringsDato = null;
        addFad(fad, fadMængde);
    }

    public Produkt createFlaske(int nr, int pris, LocalDate tapningsDato) {
        Produkt produkt = new Produkt(nr, this, pris, tapningsDato);
        produkter.add(produkt);
        return produkt;
    }

    public void tapPåFlasker(int pris, LocalDate tapningsDato, int antalFlasker) {
        for (int i = 1; i < antalFlasker; i++) {
            createFlaske(i, pris, tapningsDato);
        }
    }

    public int beregnAntalFlasker(int batchMængde, double flaskeStr) {
        return (int) (batchMængde / flaskeStr);
    }

    public void updateModningsTid() {
        long diff = ChronoUnit.YEARS.between(getLagringsDato(), batchDato);
        this.modningsTid = (int) diff;
    }

    public int getModningstid() {
       return modningsTid;
    }

    public LocalDate getBatchDato() {
        return batchDato;
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

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public ArrayList<Produkt> getProdukter() {
        return produkter;
    }

    public int getBatchMængde() {
        return batchMængde;
    }

    public void addFad(Fad fad, int mængde) {
        if (fad.getIndholdsMængde() >= mængde) {
            updateMængde(mængde);
            setLagringsDato(fad.getLagringsDato());
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

    private void updateMængde(int mængde) {
        this.batchMængde += mængde;

    }

    public void setLagringsDato(LocalDate lagringsDato) {
        if (this.lagringsDato == null) {
            this.lagringsDato = lagringsDato;
        } else {
            if (lagringsDato.isAfter(getLagringsDato())){
                this.lagringsDato = lagringsDato;
            }
        }
        updateModningsTid();

    }

    public LocalDate getLagringsDato() {
      return this.lagringsDato;
    }

    public String getHistorie() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();

        String id = "ID: " + getBatchID();
        String modning = "Modningstid: " + getModningstid() + " år";
        String date = "Lavet den: " + getBatchDato();
        String fortynding = "Fortyndingsmængde: " + getFortyndningsMængde();
        String beskrivelse = "Kommentar: " + getBeskrivelse();
        String fade = "WhiskyBatch ID: " + getBatchID() + " er lavet af følgende fad(e):";
        ArrayList<Fad> keySet = new ArrayList<>(getFade().keySet());
        for (int i = 0; i < getFade().size(); i++) {
            sb.append("FadID: ").append(keySet.get(i).getID()).append(" | Mængde: ").append(getFade().get(keySet.get(i))).append("\n");
        }


        sb1.append("\n").append(id).append("\n").append(modning).append("\n").append(date).append("\n")
                .append("\n").append(fortynding).append("\n").append(beskrivelse).append("\n").append(fade).append("\n").append(sb);

        return String.valueOf(sb1);


    }

    @Override
    public String toString() {
        return "Batch ID: " + batchID;
    }


}

