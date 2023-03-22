package application.model;

public class Fad {
    private String fadHistorik;
    private String tidligereLeverandør;
    private String str;
    private Hylde hylde;
    private String plads;

    public Fad (String fadHistorik, String tidligereLeverandør, String str) {
        this.fadHistorik = fadHistorik;
        this.tidligereLeverandør = tidligereLeverandør;
        this.str = str;
    }

    public void setHylde(Hylde hylde) {
        if (this.hylde != hylde) {
            Hylde oldHylde = this.hylde;
            if (oldHylde != null) {
                oldHylde.removeFad(this);
            }
            this.hylde = hylde;
            if (hylde != null)
                hylde.addFad(this);
        }
    }
    public Hylde getHylde(){
        return hylde;
    }

    public String getPlads() {
        return plads;
    }

    public void setPlads(String plads) {
        this.plads = plads;
    }
    public String getFadPlacering () {
        return "Hylde: " + getHylde().getHyldeNr() + " Plads: " + getPlads();
    }
}
