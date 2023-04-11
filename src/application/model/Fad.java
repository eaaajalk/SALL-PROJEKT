package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Fad {
    private int ID;
    private ArrayList<String> fadHistorik = new ArrayList<>();
    private int str;
    private String kommentar;
    private Hylde hylde;
    private int indholdsMængde;
    private final ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private final ArrayList<Omhældning> omhældninger = new ArrayList<>();
    private LocalDate lagringsDato;
    private double modningsTid;
    private static int countID = 1;


    public Fad(String fadHistorik, int str, String kommentar) {
        this.ID = countID;
        countID++;
        this.fadHistorik.add(fadHistorik);
        this.str = str;
        this.kommentar = kommentar;
        this.lagringsDato = null;
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

    public int getStr() {
        return str;
    }

    public ArrayList<Omhældning> getOmhældninger() {
        return new ArrayList<>(omhældninger);
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

    public Hylde getHylde() {
        return hylde;
    }


    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public ArrayList<String> getFadHistorik() {
        return new ArrayList<>(fadHistorik);
    }

    public void addFadHistorik(String fadHistorik) {
        this.fadHistorik.add(fadHistorik);
    }

    public int getFadPlads() {
        return getHylde().getHyldePlads(this);
    }

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

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public int getResterendePlads(){
        return getStr() - getIndholdsMængde();
    }

    public void addIndholdsMængde(int indholdsMængde) {
        this.indholdsMængde += indholdsMængde;
    }
    public void setIndholdsMængde(int indholdsMængde) {
        this.indholdsMængde = indholdsMængde;
    }

    public int getIndholdsMængde() {
        return indholdsMængde;
    }
    public Omhældning createOmhældning (int mængde, LocalDate omhældningsDato, Fad tilFad){
        Omhældning omhældning = new Omhældning(this, mængde, omhældningsDato, tilFad);
        tilFad.addOmhældning(omhældning);
        return omhældning;
    }
    public void addOmhældning(Omhældning omhældning) {
        if (!omhældninger.contains(omhældning)) {
            omhældninger.add(omhældning);
            updateLagringsDato(omhældning.getFraFad().lagringsDato);
        }
    }

    public String getIndholdMængdeToString(){
        return "" + getIndholdsMængde() + "/" + str + "L";
    }

    public void updateLagringsDato(LocalDate lagringsDato) {
        if (getLagringsDato() == null) {
            this.lagringsDato = lagringsDato;
        } else if (lagringsDato.isAfter(getLagringsDato())) {
            this.lagringsDato = lagringsDato;
        }
        updateModningsTid();
    }

    public LocalDate getLagringsDato() {
        return lagringsDato;
    }

    public void updateModningsTid() {
        long diff = ChronoUnit.YEARS.between(getLagringsDato(), LocalDate.now());
        this.modningsTid = (double) diff;
    }

    public double getModningsTid() {
        return modningsTid;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getHistorie() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbPå = new StringBuilder();
        StringBuilder sbOm = new StringBuilder();

        String id = "ID: " + getID();
        String fadstr = "Fad str: " + getStr();
        String påfyld = "Påfyldninger:";

        for (int i = 0; i < getPåfyldninger().size(); i++) {
            sbPå.append("Destillat ID: ").append(getPåfyldninger().get(i).getDestillat().getID()).append(" | Mængde: ").append(getPåfyldninger().get(i).getMængde()).append("L | Dato: ").append(getPåfyldninger().get(i).getPåfyldningsDato()).append("\n");
        }

        String omhæld = "Omhældninger:";

        for (int i = 0; i < getOmhældninger().size(); i++) {
            sbOm.append("Omhældt ").append(getOmhældninger().get(i).getMængde()).append("L fra Fad ID: ").append(getOmhældninger().get(i).getFraFad().getID()).append(" | Dato: ").append(getOmhældninger().get(i).getOmhældningsDato()).append("\n");
        }
        String modning = "Modningstid: " + getModningsTid();

        sb.append(id).append("\n").append(fadstr).append("\n").append(modning).append(" år\n").append("\n").append(påfyld).append("\n").append(sbPå.toString()).append("\n").append(omhæld).append("\n").append(sbOm.toString());


        return sb.toString();
    }

    public String toString() {
        if (getHylde() == null) {
            return getID() + "                    " + getIndholdMængdeToString() + "                                     " + "Ikke placeret" + "                                         ";

        } else {
            return getID() +"           " + getIndholdMængdeToString() +"                     " + getModningsTid()+  "                  " +
                    "     " + getHylde().getLager().toString() + " (H" + getHylde().getHyldeNr() + ", P" + getFadPlads() + ")" ;
        }
    }

    public String toString2() {
        return getID() + "    " + modningsTid;
    }
}
