package application.controller;

import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import storage.Storage;

import java.util.ArrayList;

public class Controller {
    
    private static Controller controller = null;
    
    private Storage storage;
    
    private Controller(){}
    
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
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
     * Requires:
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
    public static Hylde createHylde(int hyldeNr, Lager lager) {
        Hylde hylde = lager.createHylde(hyldeNr, lager);
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
     * Requires:
     */
    public static Fad createFad(String fadHistorik, String tidligereLeverandør, String str) {
        Fad fad = new Fad(fadHistorik, tidligereLeverandør, str);
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
    public static void placerFad(Hylde hylde, String Plads, Fad fad){
        fad.setHylde(hylde);
        fad.setPlads(Plads);
    }
    /**
     * Fjerner et fad fra en hylde og en hyldeplads
     */
    public static void removeFadFromLager(Fad fad){
     Hylde hylde = fad.getHylde();
     hylde.removeFad(fad);
     fad.setPlads("Fadet har ingen plads");
    }

    /**
     * Fjerner et fad fra en hylde og en hyldeplads
     */
    public static void flytFad (Fad fad, Hylde hylde, String Plads){
        removeFadFromLager(fad);
        placerFad(hylde, Plads, fad);
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

        Hylde h1 = Controller.createHylde(1, l1);
        Hylde h2 = Controller.createHylde(2, l2);

        Hylde h3 = Controller.createHylde(1, l2);
        Hylde h4 = Controller.createHylde(2, l2);

        Fad f1 = Controller.createFad("Bourbon", "Texas", "250L");

        Controller.placerFad(h1, "5", f1);

    }
        public static void init() {
            initStorage();

        }

    }


