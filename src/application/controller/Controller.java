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
        if (lager.getBrugteHyldeNumre().contains(hyldeNr)){
            throw new RuntimeException("HyldeNr er allerede oprettet");
        }
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
    public Fad createFad(String fadType, int str, String kommentar) {
        if (fadType == null || str < 1) {
            throw new RuntimeException("ID, fadType og str må ikke være null");
        } else {
            Fad fad = new Fad(fadType, str, kommentar);
            storage.addFad(fad);
            return fad;
        }
    }

    /**
     * Sletter fad.
     */
    public  void deleteFad(Fad fad) {
        if (fad.getHylde() != null) {
            fad.getHylde().removeFad(fad);
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
    public void placerFad(Hylde hylde, int plads, Fad fad){
        if (fad == null || hylde == null) {
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
    public void flytFad (Fad fad, Hylde hylde, int Plads){
        removeFadFromLager(fad);
        placerFad(hylde, Plads, fad);
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
     * Opretter nyt medarbejder.<br />
     * Requires:
     */
    public Medarbejder createMedarbejder(String navn, String ID) {
        Medarbejder medarbejder = new Medarbejder(navn, ID);
        storage.addMedarbejder(medarbejder);
        return medarbejder;
    }
    /**
     * Sletter medarbejder<br />
     */
    public void deleteMedarbejder(Medarbejder medarbejder) {
        storage.removeMedarbejder(medarbejder);
    }

    /**
     * Get alle medarbejdere
     */
    public  ArrayList<Medarbejder> getMedarbejdere() {
        return storage.getMedarbejdere();
    }

    // -------------------------------------------------------------------------

    /**
     * Opretter nyt maltBatch.<br />
     * Requires:
     */
    public MaltBatch createMaltBatch(String kornSort, String batchNr, String tørv) {
        MaltBatch maltBatch = new MaltBatch(kornSort, batchNr, tørv);
        storage.addMaltBatch(maltBatch);
        return maltBatch;
    }
    /**
     * Sletter maltBatch<br />
     */
    public void deleteMaltBatch(MaltBatch maltBatch) {
        storage.removeMaltBatch(maltBatch);
    }

    /**
     * Get alle maltBatch
     */
    public  ArrayList<MaltBatch> getMaltBatch() {
        return storage.getMaltBatches();
    }

    // -------------------------------------------------------------------------
    /**
     * Opretter ny Whisky.<br />
     * Requires:
     */
    public WhiskyBatch createWhiskyBatch(String batchID, int fortyndningsMængde, int alkoholProcent, String beskrivelse, LocalDate batchDato, int fadMængde, Fad fad) {
        WhiskyBatch whiskyBatch = new WhiskyBatch(batchID, fortyndningsMængde, alkoholProcent, beskrivelse, batchDato, fadMængde, fad);
        storage.addWhiskyBatch(whiskyBatch);
        return whiskyBatch;
    }
    /**
     * Sletter whisky<br />
     */
    public void deleteWhisky(WhiskyBatch whiskyBatch) {
        storage.removeWhisky(whiskyBatch);
    }

    /**
     * Get alle maltBatch
     */
    public  ArrayList<WhiskyBatch> getWhiskyBatches() {
        return storage.getWhiskies();
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
        Hylde h3 = controller.createHylde(1, l2, 5);
        Hylde h2 = controller.createHylde(2, l2,10);

        Hylde h4 = controller.createHylde(3, l2, 5);

        Fad f1 = controller.createFad("Bourbon", 250, null);
        controller.placerFad(h1, 5, f1);
        f1.addFadHistorik("Rødvin");

        Fad f2 = controller.createFad("Rødvin", 150, "Ingen");
        controller.placerFad(h2, 7, f2);

        Medarbejder m1 = controller.createMedarbejder("Oscar", "123");
        Medarbejder m2 = controller.createMedarbejder("Johan", "333");

        MaltBatch batch1 = controller.createMaltBatch("Evergreen", "001", "muld");
        MaltBatch batch2 = controller.createMaltBatch("Stairway", "002", null);

        Destillat d1 = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        Destillat d2 = controller.createDestillat(LocalDate.of(2023, 1,20 ),
                LocalDate.of(2023, 2, 21), 80, m2, null,
                "begravet dal under destilleriet", batch2,"002",32);

        Påfyldning p1 = d1.createPåfyldning(50, f1, m1, d1,
                LocalDate.of(2023, 3, 12));
        Påfyldning p2 = d2.createPåfyldning(40, f2, m2, d2,
                LocalDate.of(2023, 3, 12));

        Omhældning o1 = f1.createOmhældning(20, (LocalDate.of(2023, 3,31)), f2);

        WhiskyBatch w1 = controller.createWhiskyBatch("1", 10, 48,
                "Første release", LocalDate.of(2026, 5, 31), 50, f2);

    }
        public void init() {
            initStorage();
        }

    public static void main(String[] args) {



        Fad f1 = new Fad("Bourbon", 250, null);
        Fad f2 = new Fad("Bourbon", 250, null);
        Fad f3 = new Fad("Bourbon", 250, null);
        Fad f4 = new Fad("Bourbon", 250, null);
        f1.addFadHistorik("Rødvin");


        System.out.println(f1.getID());
        System.out.println(f2.getID());
        System.out.println(f3.getID());
        System.out.println(f4.getID());
//        System.out.println(f1.getCountID());
    }

    }




