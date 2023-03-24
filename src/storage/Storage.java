package storage;

import application.model.Fad;
import application.model.Lager;

import java.util.ArrayList;

public class Storage {

    private static ArrayList<Lager> lagere;
    private static ArrayList<Fad> fade;


    public Storage () {
        fade = new ArrayList<>();
        lagere = new ArrayList<>();
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Lager> getLagere() {
        return new ArrayList<Lager>(lagere);
    }

    public static void addLager(Lager lager) {
        lagere.add(lager);
    }

    public static void removeLager(Lager lager) {
        lagere.remove(lager);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Fad> getFade() {
        return new ArrayList<Fad>(fade);
    }

    public static void addFad(Fad fad) {
        fade.add(fad);
    }

    public static void removeFad(Fad fad) {
        fade.remove(fad);
    }

    // -------------------------------------------------------------------------




}
