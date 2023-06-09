package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Fad {
    private int ID;
    private ArrayList<String> fadHistorik = new ArrayList<>(); // Typer af indhold, som fadet tidligere har indeholdt.
    private int str; // Fadets størrelse i L
    private String kommentar;
    private Hylde hylde;
    private int indholdsMængde; // Den mængde L som fadet netop indeholder.
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>(); // Link til Påfyldning
    private final ArrayList<Omhældning> omhældninger = new ArrayList<>(); // Link til Omhældning
    private LocalDate lagringsDato; // Den seneste dato der blev fyldt noget på fadet.
    private double modningsTid; // Den tid fadet har ligget i År.
    private static int countID = 1;
    private ArrayList<String> fadHistorie = new ArrayList<>(); // Gemmer fadets historie

    public Fad(String fadHistorik, int str, String kommentar) {
        this.ID = countID;
        countID++;
        this.fadHistorik.add(fadHistorik);
        this.str = str;
        this.kommentar = kommentar;
        this.lagringsDato = null;
    }
    /**
     * Opretter et Omhældnings-objekt som komposition af dette fad. <br />
     * Requires:
     * @param mængde > 0
     * @param omhældningsDato != null
     * @param tilFad != null
     * @return Omhældning
     */
    public Omhældning createOmhældning (Fad fraFad, int mængde, LocalDate omhældningsDato, Fad tilFad){
        if (mængde > getIndholdsMængde()) {
            throw new RuntimeException("Du kan ikke omhælde mere end fadet indeholder");
        } if (mængde > tilFad.getResterendePlads()) {
            throw new RuntimeException("Der er ikke plads til så mange L i fadet");
        }
        Omhældning omhældning = new Omhældning(this, mængde, omhældningsDato, tilFad);
        tilFad.addOmhældning(omhældning);
        omhældning.updateFade();
        return omhældning;
    }
    public String getID() {
        String tempID = "" + ID;
        if (ID < 10) {
            tempID = "00" + ID;
        } else if (ID < 100) {
            tempID = "0" + ID;
        }
        return tempID;
    }
    public static String getCountID() {
        String tempID = "" + countID;
        if (countID < 10) {
            tempID = "00" + countID;
        } else if (countID < 100) {
            tempID = "0" + countID;
        }
        return tempID;
    }

    public int getStr() {
        return str;
    }
    public Hylde getHylde() {
        return hylde;
    }
    public String getKommentar() {
        return kommentar;
    }
    public LocalDate getLagringsDato() {
        return lagringsDato;
    }
    public double getModningsTid() {
        return modningsTid;
    }
    public int getIndholdsMængde() {
        return indholdsMængde;
    }
    public ArrayList<Omhældning> getOmhældninger() { return new ArrayList<>(omhældninger);}
    public ArrayList<String> getFadHistorik() { return new ArrayList<>(fadHistorik);}
    public ArrayList<Påfyldning> getPåfyldninger() {return new ArrayList<>(påfyldninger);}
    public int getFadPlads() {return getHylde().getHyldePlads(this);}
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
    public void setModningsTid(double modningsTid) {
        this.modningsTid = modningsTid;
    }

    public void setLagringsDato(LocalDate lagringsDato) {
        this.lagringsDato = lagringsDato;
    }
    public void setIndholdsMængde(int indholdsMængde) {
        this.indholdsMængde = indholdsMængde;
    }

    /**
     * Setter dette fad på hyldens plads hvis den ikke allerede er der. <br />
     */
    public void setHylde(Hylde hylde, int Plads) {
        if (this.hylde != hylde) {
            Hylde oldHylde = this.hylde;
            if (oldHylde != null) {
                oldHylde.removeFad(this);
            } this.hylde = hylde;
            if (hylde != null)
                hylde.addFad(this, Plads);
        }
    }
    public void addFadHistorik(String fadHistorik) {
        this.fadHistorik.add(fadHistorik);
    }
    /**
     * Tilføjer omhældning til dette fad <br />
     */
    public void addOmhældning(Omhældning omhældning) {
        if (!omhældninger.contains(omhældning)) {
            omhældninger.add(omhældning);
            addHistorie(omhældning);
            updateLagringsDato(omhældning.getFraFad().lagringsDato);
        }
    }
    /**
     * Tilføjer påfyldning til dette fad. <br />
     */
    public void addPåfyldning(Påfyldning påfyldning) {
        if (!påfyldninger.contains(påfyldning)) {
            påfyldninger.add(påfyldning);
            updateLagringsDato(påfyldning.getPåfyldningsDato());
        }
    }
    public void removePåfyldning(Påfyldning påfyldning) {
        if (påfyldninger.contains(påfyldning)) {
            påfyldninger.remove(påfyldning);
        }
    }
    /**
     * Tjekker om lagringsdatoen er nyere end den lagringsdato fadet allerede har registreret.
     * Hvis fadet er tomt, får den den nye lagringsdato. <br />
     */
    public void updateLagringsDato(LocalDate lagringsDato) {
        if (getLagringsDato() == null || lagringsDato.isAfter(getLagringsDato())) {
            setLagringsDato(lagringsDato);
            updateModningsTid();
        }
    }
    /**
     * Opdaterer fadets modningstid. <br />
     */
    public void updateModningsTid() {
            long diff = ChronoUnit.YEARS.between(getLagringsDato(), LocalDate.now());
            setModningsTid((double) diff);
    }
    /**
     * Beregner hvor meget plads der er tilbage i fadet. <br />
     * @return resterendePlads
     */
    public int getResterendePlads(){
        return getStr() - getIndholdsMængde();
    }
    /**
     * Nulstiller fadet, så det kan lagres på ny <br />
     */
    public void nulstilFad() {
        setIndholdsMængde(0);
        påfyldninger.clear();
        omhældninger.clear();
        setKommentar(null);
        setLagringsDato(null);
        setModningsTid(0);
        fadHistorie.clear();

    }
    /**
     * Tilføjer en mængde til fadet. <br />
     */
    public void addIndholdsMængde(int indholdsMængde) {
        this.indholdsMængde += indholdsMængde;
    }
    public String getIndholdMængdeToString(){
        return "" + getIndholdsMængde() + "/" + str + "L";
    }
    public String getInformation() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbPå = new StringBuilder();
        StringBuilder sbOm = new StringBuilder();
        String id = "ID: " + getID();
        String påfyld = "Påfyldninger:";
        for (int i = 0; i < getPåfyldninger().size(); i++) {
            Destillat destillat = getPåfyldninger().get(i).getDestillat();

            String alkohol = "Alkoholprocent: " + destillat.getAlkoholProcent();
            String vand = "Vandtype: " + destillat.getVandType();
            String maltBatch = "Maltbatch nr: " + destillat.getMaltBatch().getBatchNr();
            String tørv = "Tørv: " + destillat.getMaltBatch().getTørv();
            String kornSort = "Kornsort: " + destillat.getMaltBatch().getKornSort();

            sbPå.append("Destillat ID: ").append(destillat.getID()).
                    append(" | Mængde: ").append(getPåfyldninger().get(i).getMængde()).append("L | Dato: ").
                    append(getPåfyldninger().get(i).getPåfyldningsDato()).append("\n").append("Destillat info:").append("\n").
                    append(alkohol).append("\n").append(vand).append("\n").append(maltBatch).append("\n").append(tørv).append("\n")
                    .append(kornSort).append("\n");

        } String omhæld = "Omhældninger:";
        for (int i = 0; i < getOmhældninger().size(); i++) {
            sbOm.append("Omhældt ").append(getOmhældninger().get(i).getMængde()).append("L fra Fad ID: ").
                    append(getOmhældninger().get(i).getFraFad().getID()).append(" | Dato: ").append(getOmhældninger().get(i).getOmhældningsDato()).append("\n");
        } String modning = "Modningstid: " + getModningsTid();
        sb.append(id).append("\n").append(modning).append(" år\n").append("\n").append(påfyld)
                .append("\n").append(sbPå).append("\n").append(omhæld).append("\n").append(sbOm).append("\n");

        return sb.toString();
    }

    public void addHistorie(Omhældning omhældning) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbPå = new StringBuilder();
        StringBuilder sbOm = new StringBuilder();
        Fad fad = omhældning.getFraFad();
        String id = "Fad ID: " + fad.getID();
        String påfyld = "Påfyldninger:";
        for (int i = 0; i < fad.getPåfyldninger().size(); i++) {
            Destillat destillat = fad.getPåfyldninger().get(i).getDestillat();

            String alkohol = "Alkoholprocent: " + destillat.getAlkoholProcent();
            String vand = "Vandtype: " + destillat.getVandType();
            String maltBatch = "Maltbatch nr: " + destillat.getMaltBatch().getBatchNr();
            String tørv = "Tørv: " + destillat.getMaltBatch().getTørv();
            String kornSort = "Kornsort: " + destillat.getMaltBatch().getKornSort();

            sbPå.append("Destillat ID: ").append(destillat.getID()).
                    append(" | Mængde: ").append(fad.getPåfyldninger().get(i).getMængde()).append("L | Dato: ").
                    append(fad.getPåfyldninger().get(i).getPåfyldningsDato()).append("\n").append("Destillat info:").append("\n").
                    append(alkohol).append("\n").append(vand).append("\n").append(maltBatch).append("\n").append(tørv).append("\n")
                    .append(kornSort).append("\n");
        }
        String omhæld = "Omhældninger:";
        for (int i = 0; i < fad.getOmhældninger().size(); i++) {
            sbOm.append("Omhældt ").append(fad.getOmhældninger().get(i).getMængde()).append("L fra Fad ID: ").
                    append(fad.getOmhældninger().get(i).getFraFad().getID()).append(" | Dato: ").append(fad.getOmhældninger().get(i).getOmhældningsDato()).append("\n");
        } String modning = "Modningstid: " + fad.getModningsTid();

        sb.append(id).append("\n").append(modning).append(" år\n").append("\n").append(påfyld)
                .append("\n").append(sbPå).append("\n").append(omhæld).append("\n").append(sbOm);

        fadHistorie.add(sb.toString());
    }
    public ArrayList<String> getFadHistorie() {
        return new ArrayList<>(fadHistorie);
    }


    public String toString() {
        if (getHylde() == null) {
            return getID() + "           " + getIndholdMængdeToString() + "       " +
                    "          " + "Ikke placeret" + "                                         ";
        } else {
            return getID() +"           " + getIndholdMængdeToString() +"                     " + getModningsTid()+  "                  " +
                    "     " + getHylde().getLager().toString() + " (H" + getHylde().getHyldeNr() + ", P" + getFadPlads() + ")" ;
        }
    }
}
