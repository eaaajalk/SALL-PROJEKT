package application.controller;

import application.model.*;
import storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    private static Controller controller;
    private Storage storage;
    
    private Controller() {
        storage = Storage.getStorage();
    }
    
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
    public Lager createLager(String navn, String adresse) {
        Lager lager = new Lager(navn, adresse);
        storage.addLager(lager);
        return lager;
    }
    /**
     * Sletter lager<br />
     */
    public void deleteLager(Lager lager) {
        storage.removeLager(lager);
    }

    /**
     * Get alle lagere
     */
    public  ArrayList<Lager> getLagere() {
        return storage.getLagere();
    }
    // -------------------------------------------------------------------------
    /**
     * Opretter ny hylde på et lager.<br />
     * Requires: Et lager.
     */
    public Hylde createHylde(int hyldeNr, Lager lager, int antalHyldePladser) {
        Hylde hylde = lager.createHylde(hyldeNr, lager, antalHyldePladser);
        return hylde;
    }
    /**
     * Sletter hylde fra lager;
     */
    public void deleteHylde(Hylde hylde) {
        Lager lager = hylde.getLager();
        if (lager != null) {
            lager.removeHylde(hylde);
        }
    }
    // -------------------------------------------------------------------------
    /**
     * Opretter nyt Fad.<br />
     */
    public Fad createFad(String ID, String fadType, int str, String kommentar) {
        if (ID == null || fadType == null || str < 1) {
            throw new RuntimeException("ID, fadType og str må ikke være null");
        } else {
            Fad fad = new Fad(ID, fadType, str, kommentar);
            storage.addFad(fad);
            return fad;
        }
    }

    /**
     * Sletter fad.
     */
    public  void deleteFad(Fad fad) {
        Hylde hylde = fad.getHylde();
        if (hylde != null) {
            hylde.removeFad(fad);
        }
        storage.removeFad(fad);
    }
    public ArrayList<Fad> getFade() {
        return storage.getFade();
    }
    // -------------------------------------------------------------------------
    /**
     * Placerer et fad på en hylde og en plads
     */
    public void placerFad(Hylde hylde, int plads, Fad fad, LocalDate placeringsDato){
        if (fad == null || placeringsDato == null  || hylde == null) {
            throw new RuntimeException("Fadet, hylde og placeringsdato må ikke være null");
        }
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
    public void removeFadFromLager(Fad fad){
     Hylde hylde = fad.getHylde();
     if (hylde.getFadeList().contains(fad)) {
         hylde.removeFad(fad);
     }
    }
    /**
     * Fjerner et fad fra en hylde og en hyldeplads
     */
    public void flytFad (Fad fad, Hylde hylde, int Plads, LocalDate flytteDato){
        removeFadFromLager(fad);
        placerFad(hylde, Plads, fad, flytteDato);
    }

    // -------------------------------------------------------------------------
    /**
     * Opretter nyt destillat.<br />
     * Requires:
     */
    public Destillat createDestillat(LocalDate startDato, LocalDate slutDato, int mængde, Medarbejder medarbejder, String kommentar, String vandType, MaltBatch maltBatch, String ID, int aloholProcent) {
        Destillat destillat = new Destillat(startDato, slutDato, mængde, medarbejder, kommentar, vandType, maltBatch, ID, aloholProcent);
        storage.addDestillat(destillat);
        return destillat;
    }
    /**
     * Get alle destillater
     */
    public ArrayList<Destillat> getDestillater() {
        return storage.getDestillater();
    }
    // -------------------------------------------------------------------------
    /**
     * Opretter ny påfyldning på fra et destillat.<br />
     * Requires: Et destillat og et fad.
     */
    public Påfyldning createPåfyldning(int mængde, Fad fad, Medarbejder medarbejder, Destillat destillat, LocalDate påfyldningsDato) {
        Påfyldning påfyldning = destillat.createPåflydning(mængde, fad, medarbejder, destillat, påfyldningsDato);
        return påfyldning;
    }

    // -------------------------------------------------------------------------

    // SAVE AND LOAD STORAGE HER

    // -------------------------------------------------------------------------

    /**
     * Initializes the storage with some objects.
     */
    public void initStorage() {
        Lager l1 = controller.createLager("Bondegården", "Glæsborgvej 20, 8450 Hammel");
        Lager l2 = controller.createLager("Containeren", "Vesteragervej 1, 8450 Hammel");

        Hylde h1 = controller.createHylde(1, l1, 10);
        Hylde h2 = controller.createHylde(2, l2,10);

        Hylde h3 = controller.createHylde(1, l2, 5);
        Hylde h4 = controller.createHylde(2, l2, 5);

        Fad f1 = controller.createFad("001", "Bourbon", 250, null);
        controller.placerFad(h1, 5, f1, LocalDate.of(2023, 3, 15));

        Fad f2 = controller.createFad("002", "Rødvin", 150, "Ingen");
        controller.placerFad(h2, 7, f2, LocalDate.of(2023, 3, 15));


    }
        public void init() {
            initStorage();
        }

    }




