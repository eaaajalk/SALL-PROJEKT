package application.model;

import application.controller.Controller;
import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DestillatTest {

    Destillat destillat;
    Controller controller;

//    Lager l1;
//    Lager l2;
//    Hylde h1;
//    Hylde h2;
    Fad f1;
    Fad f2;
    Medarbejder m1;
    Medarbejder m2;
    MaltBatch batch1;
    MaltBatch batch2;

    @BeforeEach
    void setUp() {
        controller = Controller.getTestController();
//        l1 = controller.createLager("Bondegården", "vej 1");
//        l2 = controller.createLager("Containeren", "vej 2");
//        h1 = l2.createHylde(1, l2, 10);
//        h2 = l1.createHylde(2, l1, 10);
        f1 = controller.createFad("001", "Borbon", 250, "Privat fad");
        f2 = controller.createFad("002", "Borbon", 250, "Privat fad");
        m1 = controller.createMedarbejder("Oscar", "123");
        m2 = controller.createMedarbejder("Johan", "333");
        batch1 = controller.createMaltBatch("Evergreen", "001", "muld");
        batch2 = controller.createMaltBatch("Stairway", "002", null);
    }

    @Test
    void createDestillatTC1() {
//        assertEquals();
    }
}