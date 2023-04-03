package application.model;

import java.time.LocalDate;

public class Omhældning {
    private Fad fraFad, tilFad;
    private int mængde;
    private LocalDate omhældningsDato;

    Omhældning(Fad fraFad, int mængde, LocalDate omhældningsDato, Fad tilFad) {
        this.fraFad = fraFad;
        this.mængde = mængde;
        this.omhældningsDato = omhældningsDato;
        this.tilFad = tilFad;
        updateMængder();
        updatePåfyldninger();

    }

    private void updatePåfyldninger() {
        // Tilføj exceptions
        if (fraFad.getIndholdsMængde() < 0) {
            // FUCKING FEJL
        }
        if (fraFad.getIndholdsMængde() == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < fraFad.getPåfyldninger().size(); i++) {
                sb.append(fraFad.getPåfyldninger().get(i).getDestillat().getID()).append(", ");
                fraFad.removePåfyldning(fraFad.getPåfyldninger().get(i));
            }
            //Tilføjer indhold til fadHistorikken
            fraFad.addFadHistorik("Destillat fra ID: " + sb);
        }
    }

    public Fad getFraFad() {
        return fraFad;
    }

    public void setFraFad(Fad fraFad) {
        this.fraFad = fraFad;
    }

    public Fad getTilFad() {
        return tilFad;
    }

    public void setTilFad(Fad tilFad) {
        this.tilFad = tilFad;
    }

    public int getMængde() {
        return mængde;
    }

    public void setMængde(int mængde) {
        this.mængde = mængde;
    }

    public LocalDate getOmhældningsDato() {
        return omhældningsDato;
    }

    public void setOmhældningsDato(LocalDate omhældningsDato) {
        this.omhældningsDato = omhældningsDato;
    }

    public void updateMængder(){
        // Tilføj expeptions
        fraFad.setIndholdsMængde(fraFad.getIndholdsMængde()-mængde);
        tilFad.addIndholdsMængde(mængde);
    }
}
