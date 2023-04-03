package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage {
    private static Storage storage;
    private ArrayList<Lager> lagere;
    private ArrayList<Fad> fade;
    private ArrayList<Destillat> destillater;
    private ArrayList<MaltBatch> maltBatches;
    private ArrayList<Medarbejder> medarbejdere;
    private ArrayList<WhiskyBatch> whiskies;

    private Storage () {
        lagere = new ArrayList<>();
        fade = new ArrayList<>();
        destillater = new ArrayList<>();
        maltBatches = new ArrayList<>();
        medarbejdere = new ArrayList<>();
        whiskies = new ArrayList<>();
    }
    public static Storage getStorage() {
        if (storage == null){
            storage = new Storage();
        }
        return storage;
    }

    // -------------------------------------------------------------------------

    public ArrayList<Lager> getLagere() {
        return new ArrayList<Lager>(lagere);
    }

    public void addLager(Lager lager) {
        lagere.add(lager);
    }

    public void removeLager(Lager lager) {
        lagere.remove(lager);
    }

    // -------------------------------------------------------------------------

    public ArrayList<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }

    public void addFad(Fad fad) {
        fade.add(fad);
    }

    public void removeFad(Fad fad) {
        fade.remove(fad);
    }

    // -------------------------------------------------------------------------
    public ArrayList<MaltBatch> getMaltBatches() {
        return new ArrayList<MaltBatch>(maltBatches);
    }

    public void addMaltBatch(MaltBatch maltBatch) {
        maltBatches.add(maltBatch);
    }

    public void removeMaltBatch(MaltBatch maltBatch) {
        maltBatches.remove(maltBatch);
    }

    // -------------------------------------------------------------------------
    public ArrayList<Destillat> getDestillater() {
        return new ArrayList<Destillat>(destillater);
    }

    public void addDestillat(Destillat destillat) {
        destillater.add(destillat);
    }

    public void removeDestillat(Destillat destillat) {
        destillater.remove(destillat);
    }

    // -------------------------------------------------------------------------
    public ArrayList<Medarbejder> getMedarbejdere() {
        return new ArrayList<Medarbejder>(medarbejdere);
    }

    public void addMedarbejder(Medarbejder medarbejder) {
        medarbejdere.add(medarbejder);
    }

    public void removeMedarbejder(Medarbejder medarbejder) {
        medarbejdere.remove(medarbejder);
    }
    // -------------------------------------------------------------------------

    public ArrayList<WhiskyBatch> getWhiskies() {
        return new ArrayList<WhiskyBatch>(whiskies);
    }

    public void addWhisky(WhiskyBatch whiskyBatch) {
        whiskies.add(whiskyBatch);
    }

    public void removeWhisky(WhiskyBatch whiskyBatch) {
        whiskies.remove(whiskyBatch);
    }
    // -------------------------------------------------------------------------


}
