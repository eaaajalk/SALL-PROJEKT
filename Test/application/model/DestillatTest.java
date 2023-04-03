package application.model;

import application.controller.Controller;
import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DestillatTest {
    Destillat destillat;
    Controller controller;
    Fad f1;
    Fad f2;
    Medarbejder m1;
    Medarbejder m2;
    MaltBatch batch1;
    MaltBatch batch2;
    Påfyldning påfyldning;

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
//        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
//                LocalDate.of(2023, 3, 12), 150, m1, null,
//                "begravet dal under destilleriet", batch1,"001",32);
//        påfyldning = destillat.createPåflydning(50, f1, m1, destillat, LocalDate.of(2023,3,12));
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat construktor

    @Test
    void DestillatTC1() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        assertNotNull(destillat);
    }

    @Test
    void DestillatTC2() {
        destillat = new Destillat(LocalDate.of(2023, 1,20 ),
                LocalDate.of(2023, 2, 21), 80, m2, "Ny varme på 80C",
                "begravet dal under destilleriet", batch2,"002",32);

        assertNotNull(destillat);
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat construktor

    @Test
    void DestillatTC5() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = new Destillat(LocalDate.of(2023,1,27),
                    LocalDate.of(2023, 1, 20), 150, m1, null,
                    "begravet dal under destilleriet", batch1,"001",32);
        });

        assertEquals(exception.getMessage(),"Slut dato er mindre end start dato");
    }

    @Test
    void DestillatTC6() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                    LocalDate.of(2023, 3, 12), -10, m1, null,
                    "begravet dal under destilleriet", batch1,"001",32);
        });

        assertEquals(exception.getMessage(), "Mængde må ikke være 0 eller mindre");
    }

    @Test
    void DestillatTC12() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = new Destillat(LocalDate.of(2023, 1,20 ),
                    LocalDate.of(2023, 2, 21), 80, m2, "Ny varme på 80C",
                    "begravet dal under destilleriet", batch2,"-013",40);
        });

        assertEquals(exception.getMessage(), "ID må ikke være negativ");
    }

    @Test
    void DestillatTC14() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = new Destillat(LocalDate.of(2023, 1,20 ),
                    LocalDate.of(2023, 2, 21), 80, m2, "Ny varme på 80C",
                    "begravet dal under destilleriet", batch2,"002",-10);
        });

        assertEquals(exception.getMessage(), "Alkoholprocenten skal være mellem 0 til 100");
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode getPåfyldninger()
    @Test
    void getPåfyldingerTC1() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
//        påfyldning = null;
        Påfyldning påfyldning = destillat.createPåflydning(10,f1,m1,destillat,
                LocalDate.of(2023, 3, 12));
//        destillat.addPåfyldning(påfyldning);
        assertEquals(destillat.getPåfyldninger().get(0), påfyldning);
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode createPåfyldning()

    @Test
    void createPåfyldningTC1() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        Påfyldning påfyldning = destillat.createPåflydning(50, f1, m1,
                destillat, LocalDate.of(2023, 3, 12));
        assertNotNull(påfyldning);
        assertEquals(destillat.getPåfyldninger().get(0), påfyldning);
    }

    @Test
    void createPåfyldningTC2() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        Påfyldning påfyldning = destillat.createPåflydning(40, f2, m2,
                destillat, LocalDate.of(2023, 3, 21));
        assertNotNull(påfyldning);
        assertSame(destillat.getPåfyldninger().get(0), påfyldning);
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat metode createPåfyldning()
    @Test
    void createPåfyldninTC3() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Påfyldning påfyldning = destillat.createPåflydning(-10, f1, m1,
                    destillat, LocalDate.of(2023, 3, 12));
        });
        assertEquals(exception.getMessage(), "Mængden må ikke være 0 eller negativt");
    }

    @Test
    void createPåfyldningTC4() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Påfyldning påfyldning = destillat.createPåflydning(2000, f1, m1,
                    destillat, LocalDate.of(2023, 3, 12));
        });
        assertEquals(exception.getMessage(), "Mængden må ikke være større end fad størrelsen");
    }

    @Test
    void createPåfyldningTC9() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Påfyldning påfyldning = destillat.createPåflydning(40, f1, m1,
                    destillat, LocalDate.of(2023, 3, 11));
        });
        assertEquals(exception.getMessage(), "Påfyldningsdatoen må ikke fremkomme før destillat slutdatoen");
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode addPåfyldning()
    @Test
    void addPåfyldningTC1() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Påfyldning påfyldning = destillat.createPåflydning(50, f1, m1,
                destillat, LocalDate.of(2023, 3, 12));

        destillat.addPåfyldning(påfyldning);
        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
        assertSame(destillat.getPåfyldninger().get(0), påfyldning);
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat metode addPåfyldning()
    @Test
    void addPåfyldningTC2() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        Påfyldning påfyldning = destillat.createPåflydning(40, f2, m2,
                destillat, LocalDate.of(2023, 3, 21));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat.addPåfyldning(null);
        });
        assertEquals(exception.getMessage(), "Påfyldning må ikke være null");
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode removePåfyldning()
    @Test
    void removePåfyldningTC1() {
        destillat = new Destillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        Påfyldning påfyldning = destillat.createPåflydning(40, f2, m2,
                destillat, LocalDate.of(2023, 3, 21));

        destillat.removePåfyldning(påfyldning);
        assertFalse(destillat.getPåfyldninger().contains(påfyldning));
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat metode removePåfyldning()
//    @Test
//    void removePåfyldningTC2() {
//        Exception exception = assertThrows(IllegalArgumentException, )
//    }
}