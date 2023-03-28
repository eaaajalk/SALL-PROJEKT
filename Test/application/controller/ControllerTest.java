package application.controller;

import application.controller.Controller;
import application.model.Fad;
import application.model.Hylde;
import application.model.Lager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    application.controller.Controller controller;
    Lager l1;
    Lager l2;
    Hylde h1;
    Hylde h2;
    Fad f1;
    Fad f2;
    @BeforeEach
    void setUp() {
        controller = Controller.getTestController();
        l1 = controller.createLager("Bondegården", "vej 1");
        l2 = controller.createLager("Containeren", "vej 2");
        h1 = l2.createHylde(1, l2, 10);
        h2 = l1.createHylde(2, l1, 10);
        f1 = controller.createFad("001", "Borbon", 250, "Privat fad");
        f2 = controller.createFad("002", "Borbon", 250, "Privat fad");

    }

    @Test
    void createFadTC1() {
        assertEquals(8, controller.getFade().size());
        Fad fad = controller.createFad("Fad1", "Bourbon", 250, null);
        //assert
        assertNotNull(fad);
        assertTrue(controller.getFade().contains(fad));
        assertEquals("Fad1", fad.getID());
        assertEquals(250, fad.getStr());
    }
    @Test
    void createFadTC2() {
        Fad fad = controller.createFad("Fad2", "Bourbon", 50,"Privat Fad" );
        //assert
        assertNotNull(fad);
        assertTrue(controller.getFade().contains(fad));
        assertEquals("Fad2", fad.getID());
        assertEquals(50, fad.getStr());
    }

    @Test
    void createFadTC3() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            controller.createFad(null, "Bourbon", 50,"Privat Fad");
        });
        assertEquals(runtimeException.getMessage(), "ID, fadType og str må ikke være null");
    }
    @Test
    void createFadTC4() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            controller.createFad("Fad1", null, 50,"Privat Fad");
        });
        assertEquals(runtimeException.getMessage(), "ID, fadType og str må ikke være null");
    }

    @Test
    void createFadTC5() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            controller.createFad("Fad1", "Bourbon", -10,"Privat Fad");
        });
        assertEquals(runtimeException.getMessage(), "ID, fadType og str må ikke være null");
    }

    @Test
    void placerFadTC1() {
        controller.placerFad(h1, 1, f1, LocalDate.of(2023, 3, 15));
        assertTrue(h1.getFadeList().contains(f1));
    }
    @Test
    void placerFadTC2() {
        controller.placerFad(h2, 3, f2, LocalDate.of(2023, 3, 15));
        assertTrue(h2.getFadeList().contains(f2));

        double actualResult = f2.getFadPlads();
        // Assert
        assertEquals(3, actualResult, 0.0001);
    }
    @Test
    void placerFadTC3() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
        controller.placerFad(h1, 3, null, LocalDate.of(2023, 3, 15));
        });
        assertEquals(runtimeException.getMessage(), "Fadet, hylde og placeringsdato må ikke være null");
    }
    void placerFadTC6() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            controller.placerFad(h1, 3, f1, null);
        });
        assertEquals(runtimeException.getMessage(), "Fadet, hylde og placeringsdato må ikke være null");
    }
    void placerFadTC5() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            controller.placerFad(h1, -1, f1, LocalDate.of(2023, 3, 15));
        });
        assertEquals(runtimeException.getMessage(), "Pladsen findes ikke");
    }

    void placerFadTC4() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            controller.placerFad(null, 3, f1, LocalDate.of(2023, 3, 15));
        });
        assertEquals(runtimeException.getMessage(), "Fadet, hylde og placeringsdato må ikke være null");
    }


}