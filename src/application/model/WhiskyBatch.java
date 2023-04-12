package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WhiskyBatch {
    private String batchID;
    private int fortyndningsMængde; // Mængden af tilføjet vand.
    private int alkoholProcent;
    private Map<Fad, Integer> fade = new HashMap<>();
    private int modningsTid;
    private String beskrivelse;
    private LocalDate batchDato;
    private ArrayList<Produkt> produkter = new ArrayList<Produkt>();
    private double batchMængde;
    private LocalDate lagringsDato;
    private ArrayList<String> fadHistorierList = new ArrayList<>(); // Gemmer fadenes historie
    private ArrayList<String> destillatHistorierList = new ArrayList<>(); // Gemmer fadenes destillat-historier.

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

    public Produkt createFlaske(int nr, int pris, LocalDate tapningsDato, double flaskeStr) {
        Produkt produkt = new Produkt(nr, this, pris, tapningsDato, flaskeStr);
        produkter.add(produkt);
        return produkt;
    }
    public void tapPåFlasker(int pris, LocalDate tapningsDato, int antalFlasker, double flaskeStr) {
        if (flaskeStr * antalFlasker > batchMængde) {
            throw new RuntimeException("Du har ikke nok whisky til at tappe så mange flasker");
        }
        for (int i = 1; i < antalFlasker; i++) {
            createFlaske(i, pris, tapningsDato, flaskeStr);
        }
        int tapningsMængde = (int) (antalFlasker * flaskeStr);
        setBatchMængde(getBatchMængde() - tapningsMængde);
    }
    public void setBatchMængde(double batchMængde) {
        this.batchMængde = batchMængde;
    }
    public int beregnAntalFlasker(double batchMængde, double flaskeStr) {
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
    public double getBatchMængde() {
        return batchMængde;
    }
    public void addFad(Fad fad, int mængde) {
        if (fad.getIndholdsMængde() < mængde) {
            throw new RuntimeException("Mængden er større end fadets indhold");
        }
            if (!fade.containsKey(fad)) {
                fade.put(fad, mængde);
            } else {
                int m = fade.get(fad);
                fade.put(fad, m + mængde);
            }
        setLagringsDato(fad.getLagringsDato());
        addHistorier(fad);
        updateMængde(mængde, fad);
    }
    public void addHistorier(Fad fad) {
        fadHistorierList.add(fad.getHistorie());
        for (int i = 0; i < fad.getPåfyldninger().size(); i++) {
            String destillatHistorie = fad.getPåfyldninger().get(i).getDestillat().getHistorie();
            destillatHistorierList.add(destillatHistorie);
        }
    }
    public ArrayList<String> getDestillatHistorierList() {
        return new ArrayList<>(destillatHistorierList);
    }
    private void updateMængde(int mængde, Fad fad) {
        this.batchMængde += mængde;
        fad.setIndholdsMængde(fad.getIndholdsMængde()-mængde);
        if (fad.getIndholdsMængde() == 0) {
            fad.addFadHistorik(getType());
            fad.nulstilFad();
        }
    }
    public String getType() {
        String type = "";
        if (fade.size() == 1) {
            type = "Single Cask";
        }
        if (fade.size() > 1) {
            type = "Single Malt";
        }
        return type;
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

    public ArrayList<String> getFadHistorierList() {
        return new ArrayList<>(fadHistorierList);
    }

    public String getHistorie() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();

        String id = "ID: " + getBatchID();
        String modning = "Modningstid: " + getModningstid() + " år";
        String date = "Lavet den: " + getBatchDato();
        String fortynding = "Fortyndingsmængde: " + getFortyndningsMængde();
        String beskrivelse = "Kommentar: " + getBeskrivelse();
        String fade = "Lavet af følgende fad(e):";
        ArrayList<Fad> keySet = new ArrayList<>(getFade().keySet());
        for (int i = 0; i < getFade().size(); i++) {
            sb.append("FadID: ").append(keySet.get(i).getID()).append(" | Mængde: ").append(getFade().get(keySet.get(i))).append("L \n");
        }


        sb1.append(id).append("\n").append(modning).append("\n").append(date).append("\n")
                .append(fortynding).append("L\n").append(beskrivelse).append("\n").append(fade).append("\n").append(sb);

        return String.valueOf(sb1);


    }
    @Override
    public String toString() {
        return "Batch ID: " + batchID;
    }
}

