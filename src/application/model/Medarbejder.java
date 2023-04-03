package application.model;

public class Medarbejder {
    private String navn;
    private String ID;

    public Medarbejder(String navn, String ID) {
        this.navn = navn;
        this.ID = ID;
    }

    public String getNavn() {
        return navn;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        return navn + " (ID: " + ID + ")";
    }
}
