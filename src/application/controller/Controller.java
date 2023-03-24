package application.controller;

import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private static Controller controller = null;
    private Storage storage;
    
    private Controller() { storage = new Storage();}

    
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public static Controller getTestController() {
        return new Controller();
    }
    // -------------------------------------------------------------------------

    /**
     * Opretter nyt lager.<br />
     * Requires: 
     */
    public static Lager createLager(String navn, String adresse) {
        Lager lager = new Lager(navn, adresse);
        Storage.addLager(lager);
        return lager;
    }
    /**
     * Sletter lager<br />
     */
    public static void deleteLager(Lager lager) {
        Storage.removeLager(lager);
    }

    /**
     * Get alle lagere
     */
    public static ArrayList<Lager> getLagere() {
        return Storage.getLagere();
    }
    // -------------------------------------------------------------------------
    /**
     * Opretter ny hylde på et lager.<br />
     * Requires: Et lager.
     */
    public static Hylde createHylde(int hyldeNr, Lager lager, int antalHyldePladser) {
        Hylde hylde = lager.createHylde(hyldeNr, lager, antalHyldePladser);
        return hylde;
    }
    /**
     * Sletter hylde fra lager;
     */
    public static void deleteHylde(Hylde hylde) {
        Lager lager = hylde.getLager();
        if (lager != null) {
            lager.removeHylde(hylde);
        }
    }
    // -------------------------------------------------------------------------
    /**
     * Opretter nyt Fad.<br />
     */
    public static Fad createFad(String ID, String fadType, String str, String kommentar) {
        Fad fad = new Fad(ID, fadType, str, kommentar);
        Storage.addFad(fad);
        return fad;
    }

    /**
     * Sletter fad.
     */
    public static void deleteFad(Fad fad) {
        Hylde hylde = fad.getHylde();
        if (hylde != null) {
            hylde.removeFad(fad);
        }
        Storage.removeFad(fad);
    }
    public static ArrayList<Fad> getFade() {
        return Storage.getFade();
    }
    // -------------------------------------------------------------------------
    /**
     * Placerer et fad på en hylde og en plads
     */
    public static void placerFad(Hylde hylde, int plads, Fad fad, LocalDate placeringsDato){
        if (plads > hylde.getAntalPladser() || plads < 1) {
            throw new RuntimeException("Pladsen findes ikke");
        }
        if (hylde.getFadeMap().containsValue(fad)) {
            throw new RuntimeException("Fadet er allerede placeret på hylden");
        }
        if (hylde.getFadeMap().get(plads) != null) {
            throw new RuntimeException("Der står allerede et fad på pladsen");
        }
        fad.setHylde(hylde, plads);
        fad.setLagerDato(placeringsDato);
    }
    /**
     * Fjerner et fad fra en hylde og en hyldeplads
     */
    public static void removeFadFromLager(Fad fad){
     Hylde hylde = fad.getHylde();
     if (hylde.getFadeList().contains(fad)) {
         hylde.removeFad(fad);
     }
    }
    /**
     * Fjerner et fad fra en hylde og en hyldeplads
     */
    public static void flytFad (Fad fad, Hylde hylde, int Plads, LocalDate flytteDato){
        removeFadFromLager(fad);
        placerFad(hylde, Plads, fad, flytteDato);
    }
    // -------------------------------------------------------------------------

    // SAVE AND LOAD STORAGE HER

    // -------------------------------------------------------------------------

    /**
     * Initializes the storage with some objects.
     */
    public static void initStorage() {
        Lager l1 = Controller.createLager("Bondegården", "Glæsborgvej 20, 8450 Hammel");
        Lager l2 = Controller.createLager("Containeren", "Vesteragervej 1, 8450 Hammel");

        Hylde h1 = Controller.createHylde(1, l1, 10);
        Hylde h2 = Controller.createHylde(2, l2,10);

        Hylde h3 = Controller.createHylde(1, l2, 5);
        Hylde h4 = Controller.createHylde(2, l2, 5);

        Fad f1 = Controller.createFad("001", "Bourbon", "250L", "Ingen");
        Controller.placerFad(h1, 5, f1, LocalDate.of(2023, 3, 15));

    }
        public static void init() {
            initStorage();
        }
    public static void main(String[] args) {
        Lager l1 = Controller.createLager("Bondegården", "Glæsborgvej 20, 8450 Hammel");

        Hylde h1 = Controller.createHylde(1, l1, 10);
        Hylde h2 = Controller.createHylde(2, l1,10);

        System.out.println(h1.getAntalPladser());

        Fad f1 = Controller.createFad("001", "Bourbon", "250L", "Ingen");
        Fad f2 = Controller.createFad("002", "Bourbon", "250L", "Ingen");
        Controller.placerFad(h1, 5, f1, LocalDate.of(2023, 3, 15));

        Controller.placerFad(h1, 0, f2, LocalDate.of(2023, 3, 15));
        System.out.println(f1.getFadPlacering());
        System.out.println(f2.getFadPlacering());
        System.out.println(f1.getHylde().getLager());
        System.out.println(h1.getHyldePlads(f1));

    }
    }




